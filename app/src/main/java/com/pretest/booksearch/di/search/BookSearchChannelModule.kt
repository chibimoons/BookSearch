package com.pretest.booksearch.di.search

import com.pretest.search.domain.entity.BookChangedEvent
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.channels.Channel
import javax.inject.Singleton

@Module
class BookSearchChannelModule {
    @Singleton
    @Provides
    fun provideBookChangeChannel(): Channel<BookChangedEvent> {
        return Channel()
    }
}