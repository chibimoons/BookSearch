package com.pretest.search.presentation.detail.reducer

import com.pretest.mvi.Reducer
import com.pretest.search.presentation.detail.intent.*
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewState
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewStateType

class BookSearchDetailReducer: Reducer<BookSearchDetailViewState, BookSearchDetailIntent> {
    override fun reduce(state: BookSearchDetailViewState, intent: BookSearchDetailIntent): BookSearchDetailViewState {
        return when(intent) {
            is LikedBook -> reduceLikedBook(state, intent)
            is UnlikedBook -> reduceUnlikedBook(state, intent)
            else -> reduceUnknownIntent(state)
        }
    }

    private fun reduceLikedBook(state: BookSearchDetailViewState, intent: LikedBook) =
        state.copy(
            stateType = BookSearchDetailViewStateType.LIKED,
            book = intent.book
        )

    private fun reduceUnlikedBook(state: BookSearchDetailViewState, intent: UnlikedBook) =
        state.copy(
            stateType = BookSearchDetailViewStateType.UNLIKED,
            book = intent.book
        )

    private fun reduceOccurErrorIntent(state: BookSearchDetailViewState, intent: OccurError) =
        state.copy(
            stateType = BookSearchDetailViewStateType.ERROR,
            throwable = intent.throwable
        )

    private fun reduceUnknownIntent(state: BookSearchDetailViewState) =
        state.copy(stateType = BookSearchDetailViewStateType.UNKNOWN)
}