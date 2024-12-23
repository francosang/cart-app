package com.jfranco.multicounter.feature.cart.domain


data class CartItem(
    val id: Int? = null,
    val name: String,
    val quantity: Int,
    val price: Double,
)
