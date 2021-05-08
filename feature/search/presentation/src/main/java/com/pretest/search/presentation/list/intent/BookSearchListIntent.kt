package com.pretest.search.presentation.list.intent

import com.pretest.search.domain.entity.Book

interface BookSearchListIntent

data class KeywordChanged(val keyword: String): BookSearchListIntent

class StartSearching(): BookSearchListIntent

data class FinishSearching(val books: List<Book>): BookSearchListIntent

data class OccurError(val throwable: Throwable): BookSearchListIntent

data class ClickBook(val book: Book): BookSearchListIntent

data class ChangeBookState(val book: Book): BookSearchListIntent