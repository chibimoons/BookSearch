package com.pretest.booksearch.di.search

import com.pretest.booksearch.di.FragmentScope
import com.pretest.mvi.MVI
import com.pretest.search.presentation.list.intent.BookSearchListIntent
import com.pretest.search.presentation.list.reducer.BookSearchListReducer
import com.pretest.search.presentation.list.viewstate.BookSearchListViewState
import com.pretest.search.renderer.list.BookSearchListView
import dagger.Module
import dagger.Provides

@Module
class BookSearchListModule {

    @FragmentScope
    @Provides
    fun provideBookSearchListReducer(): BookSearchListReducer {
        return BookSearchListReducer()
    }

    @FragmentScope
    @Provides
    fun provideBookSearchListView(): BookSearchListView {
        return BookSearchListView()
    }

    @FragmentScope
    @Provides
    fun provideMvi(bookSearchListReducer: BookSearchListReducer, bookSearchListView: BookSearchListView): MVI<BookSearchListViewState, BookSearchListIntent> {
        return MVI(
            initialViewState = BookSearchListViewState(),
            middleware = emptyList(),
            reducer = bookSearchListReducer,
            view = bookSearchListView
        )
    }
}