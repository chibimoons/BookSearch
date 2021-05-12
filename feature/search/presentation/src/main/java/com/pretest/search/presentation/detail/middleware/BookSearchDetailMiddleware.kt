package com.pretest.search.presentation.detail.middleware

import com.pretest.mvi.BaseMiddleware
import com.pretest.search.domain.entity.Book
import com.pretest.search.domain.usecase.BookSearchDetailUseCase
import com.pretest.search.presentation.detail.intent.*
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class BookSearchDetailMiddleware(
    private val bookSearchDetailUseCase: BookSearchDetailUseCase
): BaseMiddleware<BookSearchDetailViewState, BookSearchDetailIntent>() {
    override suspend fun apply(
        viewState: BookSearchDetailViewState,
        intent: BookSearchDetailIntent
    ): Flow<BookSearchDetailIntent> {
        return when(intent) {
            is ClickBackButton -> clickBackButton()
            is ClickLike -> clickLike(viewState.book)
            is ClickUnlike -> clickUnlike(viewState.book)
            else -> next(intent)
        }
    }

    private suspend fun clickBackButton(): Flow<BookSearchDetailIntent>  {
        return flowOf { bookSearchDetailUseCase.goBack() }
            .flowOn(Dispatchers.Main)
            .flatMapConcat { skip() }
    }

    private suspend fun clickLike(book: Book): Flow<BookSearchDetailIntent>  {
        return flowOf { bookSearchDetailUseCase.likeBook(book) }
            .map { changedBook -> LikedBook(changedBook) }
    }

    private suspend fun clickUnlike(book: Book): Flow<BookSearchDetailIntent>  {
        return flowOf { bookSearchDetailUseCase.unlikeBook(book) }
            .map { changedBook -> UnlikedBook(changedBook) }
    }
}