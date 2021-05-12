package com.pretest.booksearch.di.search

import android.view.LayoutInflater
import com.pretest.booksearch.di.FragmentScope
import com.pretest.mvi.MVI
import com.pretest.mvi.Middleware
import com.pretest.search.BookSearchDetailFragment
import com.pretest.search.domain.entity.Book
import com.pretest.search.domain.entity.BookChangedEvent
import com.pretest.search.domain.usecase.BookSearchDetailRouter
import com.pretest.search.domain.usecase.BookSearchDetailUseCase
import com.pretest.search.presentation.detail.intent.BookSearchDetailIntent
import com.pretest.search.presentation.detail.middleware.BookSearchDetailMiddleware
import com.pretest.search.presentation.detail.reducer.BookSearchDetailReducer
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewState
import com.pretest.search.renderer.databinding.BookSearchDetailViewBinding
import com.pretest.search.renderer.detail.BookSearchDetailRenderer
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel

@Module
class BookSearchDetailModule {

    @FragmentScope
    @Provides
    fun provideBookSearchDetailRouter(fragment: BookSearchDetailFragment): BookSearchDetailRouter {
        return object: BookSearchDetailRouter {
            override suspend fun goBack() {
                fragment.activity?.onBackPressed()
            }
        }
    }

    @FragmentScope
    @Provides
    fun provideBookSearchDetailUseCase(bookSearchDetailRouter: BookSearchDetailRouter, channel: Channel<BookChangedEvent>): BookSearchDetailUseCase {
        return BookSearchDetailUseCase(bookSearchDetailRouter, channel)
    }

    @FragmentScope
    @Provides
    fun provideMiddleware(bookSearchDetailUseCase: BookSearchDetailUseCase): List<Middleware<BookSearchDetailViewState, BookSearchDetailIntent>> {
        return mutableListOf(BookSearchDetailMiddleware(bookSearchDetailUseCase))
    }

    @FragmentScope
    @Provides
    fun provideBookSearchDetailReducer(): BookSearchDetailReducer {
        return BookSearchDetailReducer()
    }

    @FlowPreview
    @FragmentScope
    @Provides
    fun provideMVI(fragment: BookSearchDetailFragment, middleware: MutableList<Middleware<BookSearchDetailViewState, BookSearchDetailIntent>>, reducer: BookSearchDetailReducer): MVI<BookSearchDetailViewState, BookSearchDetailIntent> {
        val book: Book = fragment.arguments?.get(BookSearchDetailFragment.EXTRA_BOOK_SEARCH_DETAIL_BOOK_PARAMETER_KEY) as Book
        return MVI(
            BookSearchDetailViewState(book = book),
            middleware = middleware,
            reducer
        )
    }

    @FlowPreview
    @FragmentScope
    @Provides
    fun provideBookSearchDetailView(fragment: BookSearchDetailFragment, mvi: MVI<BookSearchDetailViewState, BookSearchDetailIntent>): BookSearchDetailRenderer {
        val renderer = BookSearchDetailRenderer(BookSearchDetailViewBinding.inflate(LayoutInflater.from(fragment.context)), mvi)
        mvi.renderer = renderer
        return renderer
    }
}