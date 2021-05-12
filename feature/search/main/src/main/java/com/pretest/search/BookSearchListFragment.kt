package com.pretest.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pretest.mvi.MVI
import com.pretest.search.presentation.list.intent.BookSearchListIntent
import com.pretest.search.presentation.list.viewstate.BookSearchListViewState
import com.pretest.search.renderer.list.BookSearchListRenderer
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BookSearchListFragment: DaggerFragment() {

    @Inject
    lateinit var mvi: MVI<BookSearchListViewState, BookSearchListIntent>

    @Inject
    lateinit var renderer: BookSearchListRenderer

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