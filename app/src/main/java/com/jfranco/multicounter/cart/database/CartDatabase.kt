package com.jfranco.multicounter.cart.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jfranco.multicounter.cart.dao.CartItemsDao
import com.jfranco.multicounter.cart.entity.CartItem

@Database(
    entities = [
        CartItem::class
    ],
    version = 1,
    exportSchema = true,
)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartItemsDao(): CartItemsDao
}