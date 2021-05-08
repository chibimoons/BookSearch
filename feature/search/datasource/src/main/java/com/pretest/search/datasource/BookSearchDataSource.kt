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
    var keyword: String = ""
    var currentPage: Int = 0


    override suspend fun search(keyword: String): List<Book> {
        this.keyword = keyword
        var response: BookSearchResponse? = httpClient.get<BookSearchResponse> {
            headers.append("Authorization", "KakaoAK 034579f787e6c8a61801506702ac6002")
            url("https://dapi.kakao.com/v3/search/book?target=title")
            parameter("query", keyword)
        }
        return response?.documents
            ?.map { document ->
                Book(
                    id = document.isbn,
                    title = document.title,
                    description = document.contents,
                    imageUrl = document.thumbnail,
                    price = document.price,
                    publishDate = document.datetime,
                    authors = document.authors.map { authorName -> Author(authorName) }.toList()
                )
            }?.toList()
            ?: emptyList()
    }

    override suspend fun searchMore(): List<Book> {
        TODO("Not yet implemented")
    }
}