package com.jfranco.multicounter.feature.cart.handler

import com.jfranco.multicounter.feature.cart.data.repository.CartItemsRepository
import com.jfranco.multicounter.feature.cart.domain.CartItem
import kotlinx.coroutines.flow.Flow

class ListenCartItems(
    private val cartItemsRepository: CartItemsRepository
) {

    operator fun invoke(): Flow<List<CartItem>> =
        cartItemsRepository.all


}