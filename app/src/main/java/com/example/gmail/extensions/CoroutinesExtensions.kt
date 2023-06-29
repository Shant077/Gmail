package com.example.gmail.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectWhileStarted(
    lifecycleOwner: LifecycleOwner,
    action: suspend (value: T) -> Unit
) {
    var job: Job? = null
    lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                job = source.lifecycleScope.launch {
                    collect(action)
                }
            }
            Lifecycle.Event.ON_STOP -> {
                job?.cancel()
                job = null
            }
            else -> {
            }
        }
    })
}

suspend inline fun <T> Flow<T>.collect(crossinline action: suspend (value: T) -> Unit): Unit =
    collect(object : FlowCollector<T> {
        override suspend fun emit(value: T) = action(value)
    })

