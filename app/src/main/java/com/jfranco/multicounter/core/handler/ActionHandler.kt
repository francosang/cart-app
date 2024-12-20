package com.jfranco.multicounter.core.handler

abstract class ActionHandler<A, S> {

    suspend operator fun invoke(action: A, state: S): S = handle(action, state)

    protected abstract suspend fun handle(action: A, state: S): S

}