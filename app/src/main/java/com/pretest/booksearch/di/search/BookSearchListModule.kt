package com.pretest.booksearch.di.search

import android.os.Bundle
import android.view.LayoutInflater
import com.pretest.booksearch.R
import com.pretest.booksearch.di.FragmentScope
import com.pretest.booksearch.di.network.NetworkModule
import com.pretest.mvi.MVI
import com.pretest.mvi.Middleware
import com.pretest.search.BookSearchDetailFragment
import com.pretest.search.BookSearchListFragment
import com.pretest.search.datasource.BookSearchDataSource
import com.pretest.search.domain.entity.Book
import com.pretest.search.domain.entity.BookChangedEvent
import com.pretest.search.domain.usecase.BookSearchListRouter
import com.pretest.search.domain.usecase.BookSearchListUseCase
import com.pretest.search.domain.usecase.IBookSearchRepository
import com.pretest.search.presentation.list.intent.BookSearchListIntent
import com.pretest.search.presentation.list.middleware.BookSearchListMiddleware
import com.pretest.search.presentation.list.reducer.BookSearchListReducer
import com.pretest.search.presentation.list.viewstate.BookSearchListViewState
import com.pretest.search.renderer.databinding.BookSearchListViewBinding
import com.pretest.search.renderer.list.BookSearchListRenderer
import com.pretest.search.repository.BookSearchRepository
import com.pretest.search.repository.IBookSearchDataSource
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel

@Module(includes = [
    NetworkModule::class
])
class BookSearchListModule {

    @FragmentScope
    @Provides
    fun provideBookSearchDataSource(httpClient: HttpClient): IBookSearchDataSource {
        return BookSearchDataSource(httpClient).apply {
            this.maxSizePerPage = 10
        }
    }

    @FragmentScope
    @Provides
    fun provideBookSearchRepository(bookSearchDataSource: IBookSearchDataSource): IBookSearchRepository {
        return BookSearchRepository(bookSearchDataSource)
    }

    @FragmentScope
    @Provides
    fun provideBookSearchListRouter(bookSearchListFragment: BookSearchListFragment): BookSearchListRouter {
        return object : BookSearchListRouter {
            override suspend fun goBookSearchDetail(book: Book) {
                val bundle: Bundle = generateBookSearchDetailParameter(book)
                val fragment = BookSearchDetailFragment()
                fragment.arguments = bundle
                bookSearchListFragment.activity?.supportFragmentManager?.beginTransaction()?.let {
                    it.add(R.id.fragmentContainerView, fragment)
                    it.addToBackStack(null)
                    it.commit()
                }
            }
        }
    }

    private fun generateBookSearchDetailParameter(book: Book): Bundle {
        val bundle = Bundle()
        bundle.putSerializable(BookSearchDetailFragment.EXTRA_BOOK_SEARCH_DETAIL_BOOK_PARAMETER_KEY, book)
        return bundle
    }

    @FragmentScope
    @Provides
    fun provideBookSearchListUseCase(bookSearchRepository: IBookSearchRepository, bookSearchListRouter: BookSearchListRouter): BookSearchListUseCase {
        return BookSearchListUseCase(bookSearchRepository, bookSearchListRouter)
    }

    @FragmentScope
    @Provides
    fun provideMiddleware(bookSearchListUseCase: BookSearchListUseCase): List<Middleware<BookSearchListViewState, BookSearchListIntent>> {
        return mutableListOf(BookSearchListMiddleware(bookSearchListUseCase))
    }

    @FragmentScope
    @Provides
    fun provideBookSearchListReducer(): BookSearchListReducer {
        return BookSearchListReducer()
    }

    @FlowPreview
    @FragmentScope
    @Provides
    fun provideMvi(middlewares: MutableList<Middleware<BookSearchListViewState, BookSearchListIntent>>, reducer: BookSearchListReducer): MVI<BookSearchListViewState, BookSearchListIntent> {
        return MVI(
            initialViewState = BookSearchListViewState(),
            middleware = middlewares,
            reducer = reducer
        )
    }

    @FlowPreview
    @FragmentScope
    @Provides
    fun provideBookSearchListView(fragment: BookSearchListFragment, mvi: MVI<BookSearchListViewState, BookSearchListIntent>, bookChangedEventChannel: Channel<BookChangedEvent>): BookSearchListRenderer {
        val renderer = BookSearchListRenderer(BookSearchListViewBinding.inflate(LayoutInflater.from(fragment.context)), mvi, bookChangedEventChannel)
        mvi.renderer = renderer
        return renderer
    }
}