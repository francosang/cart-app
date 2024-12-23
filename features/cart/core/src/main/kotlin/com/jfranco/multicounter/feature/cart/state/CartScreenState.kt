package com.jfranco.multicounter.feature.cart.state

import com.jfranco.multicounter.feature.cart.domain.CartItem

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
        val EmptyLoading = CartScreenState(
            loading = true,
            itemBottomState = ItemBottomSheetState.Closed,
            items = emptyList(),
        )
    }
}