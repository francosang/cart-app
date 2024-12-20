package com.jfranco.multicounter.cart.repository

import com.jfranco.multicounter.cart.dao.CartItemsDao
import com.jfranco.multicounter.cart.entity.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartItemsRepository(
    private val cartItemsDao: CartItemsDao,
) {
    val all: Flow<List<CartItem>> = cartItemsDao.getAll()

    suspend fun insert(cartItem: CartItem) {
        cartItemsDao.insert(cartItem)
    }

    suspend fun delete(cartItem: CartItem) {
        cartItemsDao.delete(cartItem)
    }
}