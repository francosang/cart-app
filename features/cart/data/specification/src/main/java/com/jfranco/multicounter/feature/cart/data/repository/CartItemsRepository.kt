package com.jfranco.multicounter.feature.cart.data.repository

import com.jfranco.multicounter.feature.cart.domain.CartItem
import kotlinx.coroutines.flow.Flow

interface CartItemsRepository {
    val all: Flow<List<CartItem>>

    suspend fun save(cartItem: CartItem)

    suspend fun delete(cartItem: CartItem)
}