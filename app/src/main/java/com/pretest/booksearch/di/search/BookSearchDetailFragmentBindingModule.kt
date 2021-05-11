package com.pretest.booksearch.di.search

import com.pretest.booksearch.di.FragmentScope
import com.pretest.search.BookSearchDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BookSearchDetailFragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        BookSearchDetailModule::class
    ])
    abstract fun bindBookSearchDetailFragment(): BookSearchDetailFragment
}