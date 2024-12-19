package com.jfranco.multicounter.cart.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfranco.multicounter.cart.entity.CartItem
import com.jfranco.multicounter.cart.repository.CartItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartItemsRepository) : ViewModel() {
    val items: StateFlow<List<CartItem>> = repository.all.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addItem(task: CartItem) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun removeItem(cartItem: CartItem) {
        viewModelScope.launch {
            repository.delete(cartItem)
        }
    }
}