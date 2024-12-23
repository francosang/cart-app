package com.jfranco.multicounter.feature.cart.module

import android.content.Context
import androidx.room.Room
import com.jfranco.multicounter.feature.cart.data.impl.room.database.CartDatabase
import com.jfranco.multicounter.feature.cart.data.impl.room.repository.CartItemsRepositoryImpl
import com.jfranco.multicounter.feature.cart.data.repository.CartItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartRoomModule {
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
    fun provideTaskRepository(appDatabase: CartDatabase): CartItemsRepository {
        return CartItemsRepositoryImpl(appDatabase.cartItemsDao())
    }
}