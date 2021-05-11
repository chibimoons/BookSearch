package com.pretest.mvi

import kotlinx.coroutines.flow.Flow

interface Middleware<VIEW_STATE, INTENT> {
    suspend fun apply(viewState: VIEW_STATE, intent: INTENT): Flow<INTENT>
}