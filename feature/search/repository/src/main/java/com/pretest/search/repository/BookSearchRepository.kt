package com.pretest.search.repository

import com.pretest.search.domain.entity.Book
import com.pretest.search.domain.usecase.IBookSearchRepository

class BookSearchRepository(
    val bookSearchDataSource:IBookSearchDataSource
): IBookSearchRepository {
    override suspend fun search(keyword: String): List<Book> {
        return bookSearchDataSource.search(keyword)
    }

    override suspend fun searchMore(): List<Book> {
        return bookSearchDataSource.searchMore()
    }
}