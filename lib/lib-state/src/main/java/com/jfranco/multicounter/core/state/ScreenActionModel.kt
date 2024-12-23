package com.jfranco.multicounter.core.state


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID

sealed class Result<out V, out E> {
    data class Ok<V>(val value: V) : Result<V, Nothing>()
    data class Err<V, E>(val previous: V, val value: E, val uuid: UUID) : Result<V, E>()

    fun get(): V = when (this) {
        is Ok -> value
        is Err -> previous
    }
}

abstract class ActionStateViewModel<A, S>(initialState: S) : ViewModel() {

    private val _state: AtomicRef<Result<S, Throwable>> =
        atomic(Result.Ok(initialState))

    private val actions = MutableSharedFlow<A>(
        replay = 1,
    )

    val state: StateFlow<Result<S, Throwable>> =
        actions
            .map { action ->
                Log.d("ActionStateViewModel", "handling action: $action")

                val currentState = _state.value.get()

                Log.d("ActionStateViewModel", "current state: $currentState")

                val result: Result<S, Throwable> =
                    try {
                        Result.Ok(handlers(action, currentState))
                    } catch (e: Throwable) {
                        Result.Err(currentState, e, UUID.randomUUID())
                    }

                Log.d("ActionStateViewModel", "result: $result")

                _state.value = result
                result
            }
            .catch {
                Log.e("ActionStateViewModel", "error: $it")
                val state = _state.value.get()
                val res = Result.Err(state, it, UUID.randomUUID())
                emit(res)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, _state.value)

    fun action(action: A) {
        Log.d("ActionStateViewModel", "emit action: $action")
        val emitResult = actions.tryEmit(action)
        Log.d("ActionStateViewModel", "emit result: $emitResult")
    }

    protected abstract suspend fun handlers(action: A, previous: S): S

}