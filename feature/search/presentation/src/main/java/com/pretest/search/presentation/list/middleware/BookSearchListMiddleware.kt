package com.pretest.search.presentation.list.middleware

import com.pretest.mvi.BaseMiddleware
import com.pretest.search.domain.usecase.BookSearchListUseCase
import com.pretest.search.presentation.list.intent.*
import com.pretest.search.presentation.list.viewstate.BookSearchListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class BookSearchListMiddleware(
    private val bookSearchListUseCase: BookSearchListUseCase,
): BaseMiddleware<BookSearchListViewState, BookSearchListIntent>() {

    @FlowPreview
    override suspend fun apply(
        viewState: BookSearchListViewState,
        intent: BookSearchListIntent
    ): Flow<BookSearchListIntent> {
        return when(intent) {
            is KeywordChanged -> keywordChanged(intent)
            is SearchMore -> searchMore()
            is ClickBook -> clickBook(intent)
            else -> next(intent)
        }
    }

    private suspend fun keywordChanged(intent: KeywordChanged): Flow<BookSearchListIntent> {
        return flowOf { fetchBookSearch(intent) }
            .map { books -> FinishSearching(books = books) as BookSearchListIntent }
            .onStart { emit(StartSearching()) }
            .onStart { emit(intent) }
            .onCompletion {  }
            .catch { throwable -> emit(OccurError(throwable)) }
    }

    private suspend fun fetchBookSearch(intent: KeywordChanged) =
        if (intent.keyword.isEmpty()) {
            emptyList()
        } else {
            bookSearchListUseCase.search(keyword = intent.keyword)
        }

    private suspend fun searchMore(): Flow<BookSearchListIntent> {
        return flowOf { bookSearchListUseCase.searchMore() }
            .map { books -> FinishSearching(books = books) as BookSearchListIntent }
            .onStart { emit(StartSearching()) }
            .catch { throwable -> emit(OccurError(throwable)) }
    }

    @FlowPreview
    private suspend fun clickBook(intent: ClickBook): Flow<BookSearchListIntent> {
        return flowOf { bookSearchListUseCase.goDetailPage(intent.book) }
            .flowOn(Dispatchers.Main)
            .flatMapConcat { skip() }
    }
}