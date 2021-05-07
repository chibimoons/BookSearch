package com.pretest.search.repository

import com.pretest.search.domain.entity.Book

interface IBookSearchDataSource {
    suspend fun search(keyword: String): List<Book>

    suspend fun searchMore(): List<Book>
}