package com.jfranco.multicounter.cart.screen

import androidx.lifecycle.viewModelScope
import com.jfranco.multicounter.cart.entity.CartItem
import com.jfranco.multicounter.cart.handler.DecrementCartItemQuantity
import com.jfranco.multicounter.cart.handler.DeleteCartItem
import com.jfranco.multicounter.cart.handler.IncrementCartItemQuantity
import com.jfranco.multicounter.cart.handler.ListenCartItems
import com.jfranco.multicounter.cart.handler.SaveCartItem
import com.jfranco.multicounter.core.state.ScreenActionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ItemBottomSheetState {
    data object Closed : ItemBottomSheetState()
    data class Open(val item: CartItem?) : ItemBottomSheetState()
}

data class CartScreenState(
    val loading: Boolean,
    val itemBottomState: ItemBottomSheetState,
    val items: List<CartItem>,
) {

    val total: Double = items.sumOf { it.price * it.quantity }

    companion object {
        val Initial = CartScreenState(
            loading = true,
            itemBottomState = ItemBottomSheetState.Closed,
            items = emptyList(),
        )
    }
}

sealed class Action {
    data class SetItems(val items: List<CartItem>) : Action()
    data class ViewItemDetails(val item: CartItem) : Action()
    data object CloseItemDetails : Action()
    data class DeleteItem(val item: CartItem) : Action()
    data object CreateItem : Action()
    data class SaveItem(val item: CartItem) : Action()
    class IncrementQuantity(val item: CartItem) : Action()
    class DecrementQuantity(val item: CartItem) : Action()
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val saveCartItem: SaveCartItem,
    private val deleteCartItem: DeleteCartItem,
    private val listenCartItems: ListenCartItems,
    private val incrementCartItemQuantity: IncrementCartItemQuantity,
    private val decrementCartItemQuantity: DecrementCartItemQuantity,
) :
// TODO: remove need to pass initial action
    ScreenActionModel<Action, CartScreenState>(Action.CloseItemDetails, CartScreenState.Initial) {

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
