package com.pretest.search.domain.usecase

import com.pretest.search.domain.entity.Book
import com.pretest.search.domain.entity.BookChangedEvent
import com.pretest.search.domain.entity.BookChangedEventType
import kotlinx.coroutines.channels.Channel

class BookSearchDetailUseCase(
    private val bookSearchDetailRouter: BookSearchDetailRouter,
    private val bookChangeChannel: Channel<BookChangedEvent>
) {

    suspend fun likeBook(book: Book): Book {
        return updateLike(book, true)
    }

    private suspend fun updateLike(book: Book, like: Boolean): Book {
        val updatedBook = book.copy(like = like)
        bookChangeChannel.send(
            BookChangedEvent(
                BookChangedEventType.CHANGED_LIKE,
                updatedBook
            )
        )
        return updatedBook
    }

    suspend fun unlikeBook(book: Book): Book {
        return updateLike(book, false)
    }

    suspend fun goBack() {
        bookSearchDetailRouter.goBack()
    }
}