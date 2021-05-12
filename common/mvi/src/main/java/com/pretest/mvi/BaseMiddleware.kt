package com.pretest.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

abstract class BaseMiddleware<VIEW_STATE, INTENT>: Middleware<VIEW_STATE, INTENT> {

    fun next(intent: INTENT): Flow<INTENT> = flowOf(intent)

    fun skip(): Flow<INTENT> = emptyFlow()

    suspend fun <R> flowOf(caller: suspend () -> R): Flow<R> = flow { emit(caller()) }

    suspend fun <R> safeCall(caller: suspend () -> R): R = try {
        caller()
    } catch (e: Exception) {
        throw e
    }
}