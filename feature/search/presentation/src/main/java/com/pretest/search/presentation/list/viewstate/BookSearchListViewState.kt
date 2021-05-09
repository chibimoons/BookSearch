package com.pretest.search.presentation.list.viewstate

import com.pretest.search.domain.entity.Book

enum class BookSearchListViewStateType {
    INITIAL,
    CHANGED_KEYWORD,
    STARTED_SEARCHING,
    FINISHED_SEARCHING,
    CHANGED_BOOK_STATE,
    ERROR,
    UNKNOWN
}

data class BookSearchListViewState(
    val stateType: BookSearchListViewStateType = BookSearchListViewStateType.INITIAL,
    val keyword: String = "",
    val books: List<Book> = emptyList(),
    val isShowProgressBar: Boolean = false,
    val throwable: Throwable? = null
)