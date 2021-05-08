package com.pretest.search.presentation.list.reducer

import com.pretest.mvi.Reducer
import com.pretest.search.presentation.list.intent.*
import com.pretest.search.presentation.list.viewstate.BookSearchViewState
import com.pretest.search.presentation.list.viewstate.BookSearchViewStateType

class BookSearchListReducer : Reducer<BookSearchViewState, BookSearchListIntent> {

    override fun reduce(
        state: BookSearchViewState,
        intent: BookSearchListIntent
    ): BookSearchViewState {
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
        state: BookSearchViewState,
        intent: KeywordChanged
    ) = state.copy(
        stateType = BookSearchViewStateType.CHANGED_KEYWORD,
        keyword = intent.keyword
    )

    private fun reduceStartSearchingIntent(state: BookSearchViewState) =
        state.copy(
            stateType = BookSearchViewStateType.STARTED_SEARCHING,
            isShowProgressBar = true
        )

    private fun reduceFinishSearchingIntent(state: BookSearchViewState, intent: FinishSearching) =
        state.copy(
            stateType = BookSearchViewStateType.FINISHED_SEARCHING,
            books = state.books + intent.books
        )

    private fun reduceChangeBookStateIntent(state: BookSearchViewState, intent: ChangeBookState) =
        state.copy(
            stateType = BookSearchViewStateType.CHANGED_BOOK_STATE,
            books = state.books.map { book ->
                if (book.id.equals(intent.book.id)) {
                    intent.book
                }
                book
            }
        )

    private fun reduceOccurErrorIntent(state: BookSearchViewState, intent: OccurError) =
        state.copy(
            stateType = BookSearchViewStateType.ERROR,
            throwable = intent.throwable
        )

    private fun reduceUnknownIntent(state: BookSearchViewState) =
        state.copy(stateType = BookSearchViewStateType.UNKNOWN)
}