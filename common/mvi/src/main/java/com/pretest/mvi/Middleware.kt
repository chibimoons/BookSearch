package com.pretest.mvi

import kotlinx.coroutines.flow.Flow

interface Middleware<VIEW_STATE, ACTION> {
    suspend fun apply(viewState: VIEW_STATE, action: ACTION): Flow<ACTION>
}