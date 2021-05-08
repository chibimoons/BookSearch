package com.pretest.mvi

interface Dispatcher<INTENT> {
    fun dispatch(intent: INTENT)
}