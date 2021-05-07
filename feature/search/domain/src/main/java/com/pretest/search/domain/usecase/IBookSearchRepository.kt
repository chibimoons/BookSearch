package com.pretest.search.domain.usecase

import com.pretest.search.domain.entity.Book

interface IBookSearchRepository {

    suspend fun search(keyword: String): List<Book>

    suspend fun searchMore(): List<Book>
}