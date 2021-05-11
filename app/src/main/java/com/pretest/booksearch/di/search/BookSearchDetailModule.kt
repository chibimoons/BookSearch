package com.pretest.booksearch.di.search

import android.view.LayoutInflater
import com.pretest.booksearch.MainActivity
import com.pretest.booksearch.di.FragmentScope
import com.pretest.mvi.MVI
import com.pretest.search.presentation.detail.intent.BookSearchDetailIntent
import com.pretest.search.presentation.detail.reducer.BookSearchDetailReducer
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewState
import com.pretest.search.renderer.databinding.BookSearchDetailViewBinding
import com.pretest.search.renderer.detail.BookSearchDetailRenderer
import dagger.Module
import dagger.Provides

@Module
class BookSearchDetailModule {

    @FragmentScope
    @Provides
    fun provideBookSearchDetailReducer(): BookSearchDetailReducer {
        return BookSearchDetailReducer()
    }

    @FragmentScope
    @Provides
    fun provideMVI(reducer: BookSearchDetailReducer): MVI<BookSearchDetailViewState, BookSearchDetailIntent> {
        return MVI(
            BookSearchDetailViewState(),
            middleware = emptyList(),
            reducer
        )
    }

    @FragmentScope
    @Provides
    fun provideBookSearchDetailView(activity: MainActivity, mvi: MVI<BookSearchDetailViewState, BookSearchDetailIntent>): BookSearchDetailRenderer {
        val renderer = BookSearchDetailRenderer(BookSearchDetailViewBinding.inflate(LayoutInflater.from(activity)), mvi)
        mvi.renderable = renderer
        return renderer
    }
}