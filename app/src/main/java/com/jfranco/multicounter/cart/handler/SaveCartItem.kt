package com.jfranco.multicounter.cart.handler

import com.jfranco.multicounter.cart.repository.CartItemsRepository
import com.jfranco.multicounter.cart.screen.Action
import com.jfranco.multicounter.cart.screen.CartScreenState
import com.jfranco.multicounter.cart.screen.ItemBottomSheetState
import com.jfranco.multicounter.core.handler.ActionHandler
import javax.inject.Inject

class SaveCartItem @Inject constructor(
    private val cartItemsRepository: CartItemsRepository
) : ActionHandler<Action.SaveItem, CartScreenState>() {

    override suspend fun handle(
        action: Action.SaveItem,
        state: CartScreenState
    ): CartScreenState {
        cartItemsRepository.insert(action.item)
        return state.copy(itemBottomState = ItemBottomSheetState.Closed)
    }
}