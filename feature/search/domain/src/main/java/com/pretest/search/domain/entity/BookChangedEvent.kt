package com.pretest.search.domain.entity

enum class BookChangedEventType {
    CHANGED_LIKE,
}

data class BookChangedEvent(
    val bookChangedEventType: BookChangedEventType,
    val book: Book
) {
}