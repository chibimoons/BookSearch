package com.pretest.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pretest.mvi.MVI
import com.pretest.search.presentation.detail.intent.BookSearchDetailIntent
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewState
import com.pretest.search.renderer.detail.BookSearchDetailRenderer
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BookSearchDetailFragment: DaggerFragment() {

    @Inject
    lateinit var mvi: MVI<BookSearchDetailViewState, BookSearchDetailIntent>

    @Inject
    lateinit var renderer: BookSearchDetailRenderer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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