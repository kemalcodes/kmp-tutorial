package com.kemalcodes.kmptutorial.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface Closeable {
    fun close()
}

class FlowCollector<T>(
    private val flow: Flow<T>,
    private val onEach: (T) -> Unit
) : Closeable {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val job = scope.launch {
        flow.collect { onEach(it) }
    }

    override fun close() {
        job.cancel()
    }

    companion object {
        fun <T> collect(flow: Flow<T>, onEach: (T) -> Unit): Closeable =
            FlowCollector(flow, onEach)
    }
}
