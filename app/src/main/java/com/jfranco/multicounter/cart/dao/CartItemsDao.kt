package com.jfranco.multicounter.cart.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jfranco.multicounter.cart.entity.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemsDao {
    @Query("SELECT * FROM cart_items")
    fun getAll(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)
}