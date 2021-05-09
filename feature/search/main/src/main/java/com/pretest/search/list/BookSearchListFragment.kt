package com.pretest.search.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pretest.mvi.MVI
import com.pretest.search.presentation.list.intent.BookSearchListIntent
import com.pretest.search.presentation.list.viewstate.BookSearchListViewState
import com.pretest.search.renderer.list.BookSearchListView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BookSearchListFragment: DaggerFragment() {

    @Inject
    lateinit var mvi: MVI<BookSearchListViewState, BookSearchListIntent>

    @Inject
    lateinit var view: BookSearchListView

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}