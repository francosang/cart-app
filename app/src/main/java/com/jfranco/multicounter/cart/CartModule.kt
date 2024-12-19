package com.jfranco.multicounter.cart

import android.content.Context
import androidx.room.Room
import com.jfranco.multicounter.cart.dao.CartItemsDao
import com.jfranco.multicounter.cart.database.CartDatabase
import com.jfranco.multicounter.cart.repository.CartItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CartDatabase {
        return Room.databaseBuilder(
            appContext,
            CartDatabase::class.java,
            "cart_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: CartDatabase): CartItemsDao {
        return appDatabase.cartItemsDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(appDatabase: CartDatabase): CartItemsRepository {
        return CartItemsRepository(appDatabase.cartItemsDao())
    }
}