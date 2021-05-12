package com.pretest.search

import android.os.Bundle
import android.view.*
import com.pretest.mvi.MVI
import com.pretest.search.presentation.detail.intent.BookSearchDetailIntent
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewState
import com.pretest.search.renderer.detail.BookSearchDetailRenderer
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BookSearchDetailFragment: DaggerFragment() {

    companion object {
        const val EXTRA_BOOK_SEARCH_DETAIL_BOOK_PARAMETER_KEY: String = "extra_book_search_detail_book_parameter_key"
    }

    @Inject
    lateinit var mvi: MVI<BookSearchDetailViewState, BookSearchDetailIntent>

    @Inject
    lateinit var renderer: BookSearchDetailRenderer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return renderer.getView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvi.start()
    }

    override fun onDestroyView() {
        mvi.end()
        super.onDestroyView()
    }
}