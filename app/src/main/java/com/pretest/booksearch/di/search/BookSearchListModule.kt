package com.pretest.booksearch.di.search

import android.view.LayoutInflater
import com.pretest.booksearch.MainActivity
import com.pretest.booksearch.R
import com.pretest.booksearch.di.FragmentScope
import com.pretest.booksearch.di.network.NetworkModule
import com.pretest.mvi.MVI
import com.pretest.mvi.Middleware
import com.pretest.search.BookSearchDetailFragment
import com.pretest.search.datasource.BookSearchDataSource
import com.pretest.search.domain.entity.Book
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

@Module(includes = [
    NetworkModule::class
])
class BookSearchListModule {

    @FragmentScope
    @Provides
    fun provideBookSearcDataSource(httpClient: HttpClient): IBookSearchDataSource {
        return BookSearchDataSource(httpClient)
    }

    @FragmentScope
    @Provides
    fun provideBookSearchRepository(bookSearchDataSource: IBookSearchDataSource): IBookSearchRepository {
        return BookSearchRepository(bookSearchDataSource)
    }

    @FragmentScope
    @Provides
    fun provideBookSearchListRouter(activity: MainActivity): BookSearchListRouter {
        return object : BookSearchListRouter {
            override suspend fun goBookSearchDetail(book: Book) {
                activity.supportFragmentManager.beginTransaction().let {
                    it.add(R.id.fragmentContainerView, BookSearchDetailFragment())
                    it.addToBackStack(null)
                    it.commit()
                }
            }
        }
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

    @FragmentScope
    @Provides
    fun provideMvi(middlewares: MutableList<Middleware<BookSearchListViewState, BookSearchListIntent>>, reducer: BookSearchListReducer): MVI<BookSearchListViewState, BookSearchListIntent> {
        return MVI(
            initialViewState = BookSearchListViewState(),
            middleware = middlewares,
            reducer = reducer
        )
    }

    @FragmentScope
    @Provides
    fun provideBookSearchListView(activity: MainActivity, mvi: MVI<BookSearchListViewState, BookSearchListIntent>): BookSearchListRenderer {
        val renderer = BookSearchListRenderer(BookSearchListViewBinding.inflate(LayoutInflater.from(activity)), mvi)
        mvi.renderable = renderer
        return renderer
    }
}