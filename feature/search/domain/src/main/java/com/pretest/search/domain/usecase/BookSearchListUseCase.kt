package com.pretest.search.domain.usecase

import com.pretest.search.domain.entity.Book

class BookSearchListUseCase(
    val bookSearchRepository: IBookSearchRepository,
    val bookSearchListRouter: BookSearchListRouter
) {
    companion object {
        const val MAX_SIZE_PER_PAGE: Int = 50
    }

    suspend fun search(keyword: String): List<Book> {
        return bookSearchRepository.search(keyword)
    }

    suspend fun searchMore(): List<Book> = bookSearchRepository.searchMore()

    suspend fun goDetailPage(book: Book) = bookSearchListRouter.goBookSearchDetail(book)

}