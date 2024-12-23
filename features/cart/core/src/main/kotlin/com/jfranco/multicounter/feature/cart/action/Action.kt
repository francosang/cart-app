package com.jfranco.multicounter.feature.cart.action

import com.jfranco.multicounter.feature.cart.domain.CartItem

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