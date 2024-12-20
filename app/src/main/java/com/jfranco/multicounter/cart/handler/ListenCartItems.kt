package com.jfranco.multicounter.cart.handler

import com.jfranco.multicounter.cart.entity.CartItem
import com.jfranco.multicounter.cart.repository.CartItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenCartItems @Inject constructor(
    private val cartItemsRepository: CartItemsRepository
) {

    operator fun invoke(): Flow<List<CartItem>> =
        cartItemsRepository.all


}