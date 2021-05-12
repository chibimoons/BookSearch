package com.pretest.mvi

interface Renderable<VIEW_STATE> {
    fun render(viewState: VIEW_STATE)
}