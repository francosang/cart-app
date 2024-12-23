package com.jfranco.multicounter.feature.cart.ui.screen

import androidx.lifecycle.viewModelScope
import com.jfranco.multicounter.core.state.ActionStateViewModel
import com.jfranco.multicounter.feature.cart.action.Action
import com.jfranco.multicounter.feature.cart.handler.DecrementCartItemQuantity
import com.jfranco.multicounter.feature.cart.handler.DeleteCartItem
import com.jfranco.multicounter.feature.cart.handler.IncrementCartItemQuantity
import com.jfranco.multicounter.feature.cart.handler.ListenCartItems
import com.jfranco.multicounter.feature.cart.handler.SaveCartItem
import com.jfranco.multicounter.feature.cart.state.CartScreenState
import com.jfranco.multicounter.feature.cart.state.ItemBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val saveCartItem: SaveCartItem,
    private val deleteCartItem: DeleteCartItem,
    private val listenCartItems: ListenCartItems,
    private val incrementCartItemQuantity: IncrementCartItemQuantity,
    private val decrementCartItemQuantity: DecrementCartItemQuantity,
) : ActionStateViewModel<Action, CartScreenState>(CartScreenState.EmptyLoading) {

    init {
        viewModelScope.launch {
            listenCartItems().collect { items -> action(Action.SetItems(items)) }
        }
    }

    override suspend fun handlers(action: Action, previous: CartScreenState): CartScreenState {
        return when (action) {
            Action.CloseItemDetails -> previous.copy(itemBottomState = ItemBottomSheetState.Closed)
            Action.CreateItem -> previous.copy(itemBottomState = ItemBottomSheetState.Open(null))
            is Action.SetItems -> previous.copy(items = action.items, loading = false)
            is Action.ViewItemDetails -> previous.copy(
                itemBottomState = ItemBottomSheetState.Open(
                    action.item
                )
            )
            // Side effect handlers
            is Action.DeleteItem -> deleteCartItem(action, previous)
            is Action.SaveItem -> saveCartItem(action, previous)
            is Action.DecrementQuantity -> decrementCartItemQuantity(action, previous)
            is Action.IncrementQuantity -> incrementCartItemQuantity(action, previous)
        }
    }

}
