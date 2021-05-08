package com.pretest.search.presentation.detail.viewstate

import com.pretest.search.domain.entity.Book

enum class BookSearchDetailViewStateType {
    INITIAL,
    LIKE,
    UNLIKE,
    ERROR,
    UNKNOWN
}

data class BookSearchDetailViewState(
    val stateType: BookSearchDetailViewStateType = BookSearchDetailViewStateType.INITIAL,
    val book: Book,
    val throwable: Throwable? = null
)