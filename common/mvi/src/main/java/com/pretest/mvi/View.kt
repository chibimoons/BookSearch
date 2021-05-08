package com.pretest.mvi

interface View<VIEW_STATE> {
    fun render(viewState: VIEW_STATE)
}