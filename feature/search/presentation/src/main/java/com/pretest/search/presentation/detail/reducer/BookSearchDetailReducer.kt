package com.pretest.search.presentation.detail.reducer

import com.pretest.mvi.Reducer
import com.pretest.search.presentation.detail.intent.BookSearchDetailIntent
import com.pretest.search.presentation.detail.intent.ClickLike
import com.pretest.search.presentation.detail.intent.ClickUnlike
import com.pretest.search.presentation.detail.intent.OccurError
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewState
import com.pretest.search.presentation.detail.viewstate.BookSearchDetailViewStateType

class BookSearchDetailReducer: Reducer<BookSearchDetailViewState, BookSearchDetailIntent> {
    override fun reduce(state: BookSearchDetailViewState, intent: BookSearchDetailIntent): BookSearchDetailViewState {
        return when(intent) {
            is ClickLike -> reduceClickLikeIntent(state, intent)
            is ClickUnlike -> reduceClickUnlikeIntent(state, intent)
            else -> reduceUnknownIntent(state)
        }
    }

    private fun reduceClickLikeIntent(state: BookSearchDetailViewState, intent: ClickLike) =
        state.copy(
            stateType = BookSearchDetailViewStateType.LIKE,
            book = intent.book
        )

    private fun reduceClickUnlikeIntent(state: BookSearchDetailViewState, intent: ClickUnlike) =
        state.copy(
            stateType = BookSearchDetailViewStateType.UNLIKE,
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