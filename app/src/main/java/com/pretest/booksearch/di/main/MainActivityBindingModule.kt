package com.pretest.booksearch.di.main

import com.pretest.booksearch.MainActivity
import com.pretest.booksearch.di.ActivityScope
import com.pretest.booksearch.di.search.BookSearchDetailFragmentBindingModule
import com.pretest.booksearch.di.search.BookSearchListFragmentBindingModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        BookSearchListFragmentBindingModule::class,
        BookSearchDetailFragmentBindingModule::class
    ])
    abstract fun bindMainActivity(): MainActivity
}