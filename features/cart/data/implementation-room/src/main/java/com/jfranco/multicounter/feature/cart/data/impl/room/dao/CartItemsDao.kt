package com.jfranco.multicounter.feature.cart.data.impl.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jfranco.multicounter.feature.cart.data.impl.room.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemsDao {
    @Query("SELECT * FROM cart_items")
    fun getAll(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: CartItemEntity)

    @Delete
    suspend fun delete(entity: CartItemEntity)
}