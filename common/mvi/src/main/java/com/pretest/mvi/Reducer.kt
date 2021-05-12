package com.pretest.mvi

interface Reducer<VIEW_STATE, INTENT> {
    fun reduce(state: VIEW_STATE, intent: INTENT): VIEW_STATE
}