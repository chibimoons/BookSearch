package com.pretest.search.datasource

import com.pretest.search.datasource.network.model.BookSearchResponse
import com.pretest.search.domain.entity.Author
import com.pretest.search.domain.entity.Book
import com.pretest.search.repository.IBookSearchDataSource
import io.ktor.client.*
import io.ktor.client.request.*

class BookSearchDataSource(
    private val httpClient: HttpClient
) : IBookSearchDataSource {

    var maxSizePerPage: Int? = null
    private var keyword: String = ""
    private var currentPage: Int = 1
    private var isEnd: Boolean = false

    override suspend fun search(keyword: String): List<Book> {
        this.keyword = keyword
        this.currentPage = 1
        this.isEnd = false
        return requestSearchApi(currentPage)
    }

    private suspend fun requestSearchApi(page: Int): List<Book> {
        val response: BookSearchResponse = httpClient.get {
            headers.append("Authorization", "KakaoAK 034579f787e6c8a61801506702ac6002")
            url("https://dapi.kakao.com/v3/search/book")
            parameter("target", "title")
            parameter("query", keyword)
            parameter("size", maxSizePerPage)
            parameter("page", page)
        }

        currentPage = page + 1
        isEnd = response.metaData.isEnd
        return mapToBooks(response)
    }

    private fun mapToBooks(response: BookSearchResponse) =
        response.documents
            .map { document ->
                Book(
                    id = document.isbn,
                    title = document.title,
                    description = document.contents,
                    imageUrl = document.thumbnail,
                    price = document.price,
                    publishDate = document.datetime,
                    publisher = document.publisher,
                    authors = document.authors.map { authorName -> Author(authorName) }.toList()
                )
            }.toList()

    override suspend fun searchMore(): List<Book> {
        return if (isEnd) {
            emptyList()
        } else {
            requestSearchApi(currentPage)
        }
    }
}