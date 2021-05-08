package com.pretest.search.presentation.detail.intent

import com.pretest.search.domain.entity.Book

interface BookSearchDetailIntent

data class OccurError(val throwable: Throwable): BookSearchDetailIntent

data class ClickLike(val book: Book): BookSearchDetailIntent

data class ClickUnlike(val book: Book): BookSearchDetailIntent

class ClickBackButton(): BookSearchDetailIntent