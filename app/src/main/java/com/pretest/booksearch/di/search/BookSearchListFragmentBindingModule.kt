package com.pretest.booksearch.di.search

import com.pretest.booksearch.di.FragmentScope
import com.pretest.search.list.BookSearchListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BookSearchListFragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        BookSearchListModule::class
    ])
    abstract fun bindBookSearchListFragment(): BookSearchListFragment
}