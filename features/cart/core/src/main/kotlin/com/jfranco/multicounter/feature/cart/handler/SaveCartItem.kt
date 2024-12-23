package com.jfranco.multicounter.feature.cart.handler

import com.jfranco.multicounter.core.handler.ActionHandler
import com.jfranco.multicounter.feature.cart.action.Action
import com.jfranco.multicounter.feature.cart.data.repository.CartItemsRepository
import com.jfranco.multicounter.feature.cart.state.CartScreenState
import com.jfranco.multicounter.feature.cart.state.ItemBottomSheetState

class SaveCartItem(
    private val cartItemsRepository: CartItemsRepository
) : ActionHandler<Action.SaveItem, CartScreenState>() {

    override suspend fun handle(
        action: Action.SaveItem,
        state: CartScreenState
    ): CartScreenState {
        cartItemsRepository.save(action.item)
        return state.copy(itemBottomState = ItemBottomSheetState.Closed)
    }
}