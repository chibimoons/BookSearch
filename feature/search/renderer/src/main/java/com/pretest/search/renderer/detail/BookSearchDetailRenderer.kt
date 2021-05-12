package com.pretest.search.renderer.detail

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.pretest.mvi.Dispatcher
import com.pretest.mvi.Renderable
import com.pretest.search.domain.entity.Book
import com.pretest.search.presentation.detail.intent.BookSearchDetailIntent
import com.pretest.search.presentation.detail.intent.ClickBackButton
import com.pretest.search.presentation.detail.intent.ClickLike
import com.pretest.search.presentation.detail.intent.ClickUnlike
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewState
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewStateType
import com.pretest.search.renderer.R
import com.pretest.search.renderer.databinding.BookSearchDetailViewBinding
import com.pretest.search.renderer.utils.DateTimeUtils
import com.pretest.search.renderer.utils.PriceUtils

class BookSearchDetailRenderer(
    private val binding: BookSearchDetailViewBinding,
    private val dispatcher: Dispatcher<BookSearchDetailIntent>
): Renderable<BookSearchDetailViewState> {

    private lateinit var viewState: BookSearchDetailViewState

    init {
        initBackButton()
        initMenu()
    }

    private fun initBackButton() {
        binding.toolbar.setNavigationOnClickListener { dispatcher.dispatch(ClickBackButton()) }
    }

    private fun initMenu() {
        binding.toolbar.inflateMenu(R.menu.book_search_detail_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionFavorite -> {
                    dispatcher.dispatch(getClickLikeIntent(viewState.book))
                    true
                }
                else -> false
            }
        }
    }

    private fun getClickLikeIntent(book: Book): BookSearchDetailIntent {
        return if (book.like) {
            ClickUnlike(book)
        } else {
            ClickLike(book)
        }
    }

    override fun render(viewState: BookSearchDetailViewState) {
        this.viewState = viewState
        when(viewState.stateType) {
            BookSearchDetailViewStateType.INITIAL -> renderInitialState(viewState)
            BookSearchDetailViewStateType.LIKED -> updateLike(viewState.book)
            BookSearchDetailViewStateType.UNLIKED -> updateLike(viewState.book)
            else -> {}
        }
    }

    private fun renderInitialState(viewState: BookSearchDetailViewState) {
        with(viewState.book) {
            updateThumbnail(this)
            updateTitle(this)
            updateLike(this)
            updatePublishDate(this)
            updateDescription(this)
            updatePublisher(this)
            updatePrice(this)
        }
    }

    private fun updateThumbnail(book: Book) {
        Glide.with(binding.thumbnail)
            .load(book.imageUrl)
            .into(binding.thumbnail)
    }

    private fun updateTitle(book: Book) {
        binding.toolbar.title = book.title
    }

    private fun updatePublishDate(book: Book) {
        binding.publishDate.text = DateTimeUtils.getFormattedDateString(book.publishDate)
    }

    private fun updateDescription(book: Book) {
        binding.description.text = book.description
    }

    private fun updatePublisher(book: Book) {
        binding.publisher.text = book.publisher
    }

    private fun updatePrice(book: Book) {
        binding.price.text = PriceUtils.getPrice(getContext(), book.price)
    }

    private fun updateLike(book: Book) {
        val favorite = binding.toolbar.menu.findItem(R.id.actionFavorite)
        favorite.setIcon(getFavoriteIcon(book))
    }

    private fun getFavoriteIcon(book: Book) =
        if (book.like) R.drawable.ico_star_on else R.drawable.ico_star_off

    fun getView(): View {
        return binding.root
    }

    private fun getContext(): Context {
        return binding.root.context
    }
}