package com.jfranco.multicounter.feature.cart.data.impl.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jfranco.multicounter.feature.cart.data.impl.room.dao.CartItemsDao
import com.jfranco.multicounter.feature.cart.data.impl.room.entity.CartItemEntity

@Database(
    entities = [
        CartItemEntity::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartItemsDao(): CartItemsDao
}