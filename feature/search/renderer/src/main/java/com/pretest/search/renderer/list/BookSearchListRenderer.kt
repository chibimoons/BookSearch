package com.pretest.search.renderer.list

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pretest.mvi.Dispatcher
import com.pretest.mvi.Renderable
import com.pretest.search.domain.entity.Book
import com.pretest.search.domain.entity.BookChangedEvent
import com.pretest.search.presentation.list.intent.BookSearchListIntent
import com.pretest.search.presentation.list.intent.ChangeBookState
import com.pretest.search.presentation.list.intent.KeywordChanged
import com.pretest.search.presentation.list.intent.SearchMore
import com.pretest.search.presentation.list.viewstate.BookSearchListViewState
import com.pretest.search.presentation.list.viewstate.BookSearchListViewStateType
import com.pretest.search.renderer.databinding.BookSearchListViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BookSearchListRenderer(
    private val binding: BookSearchListViewBinding,
    private val dispatcher: Dispatcher<BookSearchListIntent>,
    private val bookChangedEventChannel: Channel<BookChangedEvent>
) : Renderable<BookSearchListViewState> {

    private var adapter: BookSearchListAdapter = BookSearchListAdapter { event: BookSearchListIntent -> dispatcher.dispatch(event) }

    init {
        initSearchEditText()
        initRecyclerView()
        listenBookChangedChannel()
    }

    private fun initSearchEditText() {
        binding.searchEditText.addTextChangedListener(generateTextWatcher())
    }

    private fun generateTextWatcher() = object : TextWatcher {

        private var text = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
            Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val newText = s.toString().trim()
            if (newText == text)
                return

            text = newText

            CoroutineScope(Dispatchers.Main).launch {
                delay(500)  //debounce timeOut
                if (newText != text)
                    return@launch

                dispatcher.dispatch(KeywordChanged(text))
            }
        }

        override fun afterTextChanged(s: Editable?) = Unit
    }

    private  fun listenBookChangedChannel() {
        CoroutineScope(Dispatchers.Main).launch {
            bookChangedEventChannel.receiveAsFlow()
                .collect { dispatcher.dispatch(ChangeBookState(it.book)) }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        binding.recyclerView.onScrollBottomListener = { dispatcher.dispatch(SearchMore()) }
    }

    fun getView(): View {
        return binding.root
    }

    override fun render(viewState: BookSearchListViewState) {
        updateProgressBar(viewState)
        when (viewState.stateType) {
            BookSearchListViewStateType.STARTED_SEARCHING -> renderStartedSearchingState()
            BookSearchListViewStateType.FINISHED_SEARCHING -> renderFinishedSearchingState(viewState)
            BookSearchListViewStateType.CHANGED_BOOK_STATE -> renderChangedBookState(viewState)
            BookSearchListViewStateType.ERROR -> renderErrorState(viewState)
            else -> {}
        }
    }

    private fun renderStartedSearchingState() {
        showErrorMessage(false)
    }

    private fun renderFinishedSearchingState(viewState: BookSearchListViewState) {
        updateBooks(viewState.books)
    }

    private fun renderErrorState(viewState: BookSearchListViewState) {
        showErrorMessage(true)
        updateErrorMessage(viewState.throwable)
    }

    private fun renderChangedBookState(viewState: BookSearchListViewState) {
        updateBooks(viewState.books)
    }

    private fun updateProgressBar(viewState: BookSearchListViewState) {
        binding.progressBar.visibility = when (viewState.isShowProgressBar) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    private fun updateBooks(books: List<Book>) {
        adapter.submitList(books)
    }

    private fun showErrorMessage(show: Boolean) {
        binding.errorMessage.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun updateErrorMessage(throwable: Throwable?) {
        binding.errorMessage.text = throwable?.message
        Toast.makeText(binding.root.context, throwable?.message?: "", Toast.LENGTH_SHORT).show()
        throwable?.printStackTrace()
    }
}