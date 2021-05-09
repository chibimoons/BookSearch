package com.pretest.booksearch

import com.pretest.booksearch.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class GlobalApplication: DaggerApplication() {

    private val applicationInjector = DaggerAppComponent.builder().application(this).build()

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector
}