package com.jfranco.multicounter.feature.cart.handler

import com.jfranco.multicounter.core.handler.ActionHandler
import com.jfranco.multicounter.feature.cart.action.Action
import com.jfranco.multicounter.feature.cart.data.repository.CartItemsRepository
import com.jfranco.multicounter.feature.cart.state.CartScreenState

class IncrementCartItemQuantity(
    private val cartItemsRepository: CartItemsRepository
) : ActionHandler<Action.IncrementQuantity, CartScreenState>() {

    override suspend fun handle(
        action: Action.IncrementQuantity,
        state: CartScreenState
    ): CartScreenState {
        val item = action.item.copy(quantity = action.item.quantity + 1)
        cartItemsRepository.save(item)
        return state
    }
}