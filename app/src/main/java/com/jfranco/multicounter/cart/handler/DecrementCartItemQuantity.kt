package com.jfranco.multicounter.cart.handler

import com.jfranco.multicounter.cart.repository.CartItemsRepository
import com.jfranco.multicounter.cart.screen.Action
import com.jfranco.multicounter.cart.screen.CartScreenState
import com.jfranco.multicounter.core.handler.ActionHandler
import javax.inject.Inject

class DecrementCartItemQuantity @Inject constructor(
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