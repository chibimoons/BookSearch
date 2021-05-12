package com.pretest.search.presentation.list.reducer

import com.pretest.mvi.Reducer
import com.pretest.search.presentation.list.intent.*
import com.pretest.search.presentation.list.viewstate.BookSearchListViewState
import com.pretest.search.presentation.list.viewstate.BookSearchListViewStateType

class BookSearchListReducer : Reducer<BookSearchListViewState, BookSearchListIntent> {

    override fun reduce(
        state: BookSearchListViewState,
        intent: BookSearchListIntent
    ): BookSearchListViewState {
        return when (intent) {
            is KeywordChanged -> reduceKeywordChangedIntent(state, intent)
            is StartSearching -> reduceStartSearchingIntent(state)
            is FinishSearching -> reduceFinishSearchingIntent(state, intent)
            is ChangeBookState -> reduceChangeBookStateIntent(state, intent)
            is OccurError -> reduceOccurErrorIntent(state, intent)
            else -> reduceUnknownIntent(state)
        }
    }

    private fun reduceKeywordChangedIntent(
        state: BookSearchListViewState,
        intent: KeywordChanged
    ) = state.copy(
        stateType = BookSearchListViewStateType.CHANGED_KEYWORD,
        keyword = intent.keyword
    )

    private fun reduceStartSearchingIntent(state: BookSearchListViewState) =
        state.copy(
            stateType = BookSearchListViewStateType.STARTED_SEARCHING,
            isShowProgressBar = true
        )

    private fun reduceFinishSearchingIntent(state: BookSearchListViewState, intent: FinishSearching) =
        state.copy(
            stateType = BookSearchListViewStateType.FINISHED_SEARCHING,
            books = state.books + intent.books,
            isShowProgressBar = false
        )

    private fun reduceChangeBookStateIntent(state: BookSearchListViewState, intent: ChangeBookState) =
        state.copy(
            stateType = BookSearchListViewStateType.CHANGED_BOOK_STATE,
            books = state.books.map { book ->
                if (book.id == intent.book.id) {
                    intent.book
                } else {
                    book
                }
            }
        )

    private fun reduceOccurErrorIntent(state: BookSearchListViewState, intent: OccurError) =
        state.copy(
            stateType = BookSearchListViewStateType.ERROR,
            isShowProgressBar = false,
            throwable = intent.throwable
        )

    private fun reduceUnknownIntent(state: BookSearchListViewState) =
        state.copy(
            stateType = BookSearchListViewStateType.UNKNOWN,
            isShowProgressBar = false
        )
}