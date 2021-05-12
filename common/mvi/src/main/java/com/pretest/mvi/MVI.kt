package com.pretest.mvi

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
class MVI<VIEW_STATE, INTENT>(
    var initialViewState: VIEW_STATE,
    val middleware: List<Middleware<VIEW_STATE, INTENT>>,
    val reducer: Reducer<VIEW_STATE, INTENT>
): Dispatcher<INTENT> {

    private val intentChannel: Channel<INTENT> = Channel()
    private val viewStateChannel: Channel<VIEW_STATE> = Channel()
    var renderer: Renderable<VIEW_STATE>? = null

    fun start() {
        renderer?.render(initialViewState)
        launchRedux(initialViewState)
    }

    private fun launchRedux(viewState: VIEW_STATE) {
        CoroutineScope(Dispatchers.Main).launch {
            var currentViewState = viewState
            intentChannel.receiveAsFlow()
                .flatMapConcat { applyMiddleware(0, currentViewState, it) }
                .flowOn(Dispatchers.Main)
                .catch { it.printStackTrace() }
                .map { reducer.reduce(currentViewState, it) }
                .onEach {
                    currentViewState = it
                    notify(it)
                }
                .collect { renderer?.render(it) }
        }
    }

    private suspend fun applyMiddleware(index: Int, viewState: VIEW_STATE, intent: INTENT): Flow<INTENT> {
        return if (index >= middleware.size) {
            flowOf(intent)
        } else {
            middleware[index].apply(viewState, intent)
                .flatMapMerge { chainedIntent -> applyMiddleware(index + 1, viewState, chainedIntent) }
        }
    }

    private fun notify(viewState: VIEW_STATE) {
        if (viewStateChannel.isClosedForSend) {
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            viewStateChannel.send(viewState)
        }
    }

    override fun dispatch(intent: INTENT) {
        if (intentChannel.isClosedForSend) {
            return
        }
        CoroutineScope(Dispatchers.Default).launch {
            intentChannel.send(intent)
        }
    }

    fun listen(): Flow<VIEW_STATE> {
        return viewStateChannel.receiveAsFlow()
    }

    fun end() {
        intentChannel.close()
        viewStateChannel.close()
    }
}