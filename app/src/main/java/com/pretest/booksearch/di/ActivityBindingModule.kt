package com.pretest.booksearch.di

import com.pretest.booksearch.di.main.MainActivityBindingModule
import dagger.Module

@Module(includes = [
    MainActivityBindingModule::class
])
abstract class ActivityBindingModule {

}