package com.jfranco.multicounter.cart.handler

import com.jfranco.multicounter.cart.repository.CartItemsRepository
import com.jfranco.multicounter.cart.screen.Action
import com.jfranco.multicounter.cart.screen.CartScreenState
import com.jfranco.multicounter.core.handler.ActionHandler
import javax.inject.Inject

class IncrementCartItemQuantity @Inject constructor(
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