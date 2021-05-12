package com.pretest.booksearch.di

import com.pretest.booksearch.di.search.BookSearchDetailModule
import com.pretest.booksearch.di.search.BookSearchListModule
import com.pretest.search.BookSearchDetailFragment
import com.pretest.search.BookSearchListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        BookSearchListModule::class
    ])
    abstract fun bindBookSearchListFragment(): BookSearchListFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        BookSearchDetailModule::class
    ])
    abstract fun bindBookSearchDetailFragment(): BookSearchDetailFragment

}