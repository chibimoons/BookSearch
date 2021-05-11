package com.pretest.search.renderer.detail

import android.view.View
import com.pretest.mvi.Dispatcher
import com.pretest.mvi.Renderable
import com.pretest.search.presentation.detail.intent.BookSearchDetailIntent
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewState
import com.pretest.search.renderer.databinding.BookSearchDetailViewBinding

class BookSearchDetailRenderer(
    private val binding: BookSearchDetailViewBinding,
    private val dispatcher: Dispatcher<BookSearchDetailIntent>
): Renderable<BookSearchDetailViewState> {
    override fun render(viewState: BookSearchDetailViewState) {

    }

    fun getView(): View {
        return binding.root
    }
}