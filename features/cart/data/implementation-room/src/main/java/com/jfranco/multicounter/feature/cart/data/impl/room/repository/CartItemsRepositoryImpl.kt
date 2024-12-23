package com.jfranco.multicounter.feature.cart.data.impl.room.repository

import com.jfranco.multicounter.feature.cart.data.impl.room.dao.CartItemsDao
import com.jfranco.multicounter.feature.cart.data.impl.room.entity.CartItemEntity
import com.jfranco.multicounter.feature.cart.data.repository.CartItemsRepository
import com.jfranco.multicounter.feature.cart.domain.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartItemsRepositoryImpl(
    private val cartItemsDao: CartItemsDao,
) : CartItemsRepository {

    override val all: Flow<List<CartItem>> = cartItemsDao.getAll().map { entities ->
        entities.map { entity ->
            CartItem(
                id = entity.id,
                name = entity.name,
                price = entity.price,
                quantity = entity.quantity,
            )
        }
    }

    override suspend fun save(cartItem: CartItem) {
        cartItemsDao.save(
            CartItemEntity(
                id = cartItem.id,
                name = cartItem.name,
                price = cartItem.price,
                quantity = cartItem.quantity,
            )
        )
    }

    override suspend fun delete(cartItem: CartItem) {
        cartItemsDao.delete(
            CartItemEntity(
                id = cartItem.id,
                name = cartItem.name,
                price = cartItem.price,
                quantity = cartItem.quantity,
            )
        )
    }

}