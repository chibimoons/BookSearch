package com.pretest.search.domain.usecase

import com.pretest.search.domain.entity.Book

class BookSearchListUseCase(
    val bookSearchRepository: IBookSearchRepository,
    val bookSearchListRouter: BookSearchListRouter
) {

    companion object {
        private const val MAX_SIZE_PER_PAGE: Int = 50
    }

    var keyword: String = ""
    var books: List<Book> = emptyList()

    suspend fun search(keyword: String): List<Book> {
        this.keyword = keyword
        if (books.size > 0) {
            books = emptyList()
        }

        books = bookSearchRepository.search(keyword)
        return books
    }

    suspend fun searchMore(): List<Book> {
        return listOf()
    }

    fun changedBook(changedBook: Book) {
        books = books.map {
            if (it.id.equals(changedBook.id)) {
                changedBook
            }
            it
        }
    }

    suspend fun goDetailPage(book: Book) {
        bookSearchListRouter.goBookSearchDetail(book)
    }
}