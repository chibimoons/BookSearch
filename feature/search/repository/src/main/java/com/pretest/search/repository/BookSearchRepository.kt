package com.pretest.search.repository

import com.pretest.search.domain.entity.Book
import com.pretest.search.domain.usecase.IBookSearchRepository

class BookSearchRepository(
    val bookSearchDataSource:IBookSearchDataSource
): IBookSearchRepository {

    override suspend fun search(keyword: String): List<Book> = bookSearchDataSource.search(keyword)

    override suspend fun searchMore(): List<Book> = bookSearchDataSource.searchMore()

}