package com.pretest.booksearch.di

import android.app.Application
import com.pretest.booksearch.GlobalApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityBindingModule::class
])
interface AppComponent: AndroidInjector<GlobalApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(application: GlobalApplication)
}