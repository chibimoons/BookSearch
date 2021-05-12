package com.pretest.search.domain.usecase

import com.pretest.search.domain.entity.Book

interface BookSearchListRouter {
    suspend fun goBookSearchDetail(book: Book)
}