package com.pretest.search.renderer.base

interface EventListener<EVENT> {
    fun onEvent(event: EVENT)
}