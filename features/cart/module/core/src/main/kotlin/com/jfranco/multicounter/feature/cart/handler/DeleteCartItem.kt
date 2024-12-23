package com.jfranco.multicounter.feature.cart.handler

import com.jfranco.multicounter.core.handler.ActionHandler
import com.jfranco.multicounter.feature.cart.action.Action
import com.jfranco.multicounter.feature.cart.data.repository.CartItemsRepository
import com.jfranco.multicounter.feature.cart.state.CartScreenState
import com.jfranco.multicounter.feature.cart.state.ItemBottomSheetState

class DeleteCartItem(
    private val cartItemsRepository: CartItemsRepository
) : ActionHandler<Action.DeleteItem, CartScreenState>() {

    override suspend fun handle(
        action: Action.DeleteItem,
        state: CartScreenState
    ): CartScreenState {
        cartItemsRepository.delete(action.item)
        return state.copy(itemBottomState = ItemBottomSheetState.Closed)
    }
}