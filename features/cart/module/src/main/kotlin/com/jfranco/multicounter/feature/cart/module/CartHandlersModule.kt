package com.jfranco.multicounter.feature.cart.module

import com.jfranco.multicounter.feature.cart.data.repository.CartItemsRepository
import com.jfranco.multicounter.feature.cart.handler.DecrementCartItemQuantity
import com.jfranco.multicounter.feature.cart.handler.DeleteCartItem
import com.jfranco.multicounter.feature.cart.handler.IncrementCartItemQuantity
import com.jfranco.multicounter.feature.cart.handler.ListenCartItems
import com.jfranco.multicounter.feature.cart.handler.SaveCartItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CartHandlersModule {

    @Provides
    fun provideSaveCartItem(cartItemsRepository: CartItemsRepository): SaveCartItem {
        return SaveCartItem(cartItemsRepository)
    }

    @Provides
    fun provideDeleteCartItem(cartItemsRepository: CartItemsRepository): DeleteCartItem {
        return DeleteCartItem(cartItemsRepository)
    }

    @Provides
    fun provideIncrementCartItemQuantity(cartItemsRepository: CartItemsRepository): IncrementCartItemQuantity {
        return IncrementCartItemQuantity(cartItemsRepository)
    }

    @Provides
    fun provideDecrementCartItemQuantity(cartItemsRepository: CartItemsRepository): DecrementCartItemQuantity {
        return DecrementCartItemQuantity(cartItemsRepository)
    }

    @Provides
    fun provideListenCartItems(cartItemsRepository: CartItemsRepository): ListenCartItems {
        return ListenCartItems(cartItemsRepository)
    }

}