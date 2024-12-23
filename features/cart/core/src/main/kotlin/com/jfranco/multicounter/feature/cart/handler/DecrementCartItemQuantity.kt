package com.jfranco.multicounter.feature.cart.handler

import com.jfranco.multicounter.core.handler.ActionHandler
import com.jfranco.multicounter.feature.cart.action.Action
import com.jfranco.multicounter.feature.cart.data.repository.CartItemsRepository
import com.jfranco.multicounter.feature.cart.state.CartScreenState

class DecrementCartItemQuantity(
    private val cartItemsRepository: CartItemsRepository
) : ActionHandler<Action.DecrementQuantity, CartScreenState>() {

    override suspend fun handle(
        action: Action.DecrementQuantity,
        state: CartScreenState
    ): CartScreenState {
        if (action.item.quantity == 1) {
            cartItemsRepository.delete(action.item)
            return state
        } else {
            val item = action.item.copy(quantity = action.item.quantity - 1)
            cartItemsRepository.save(item)
            return state
        }
    }
}