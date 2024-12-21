package com.jfranco.multicounter.cart.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jfranco.multicounter.cart.entity.CartItem

private data class CartItemEditing(
    val id: Int?,
    val name: String,
    val nameError: String?,
    val price: String,
    val priceError: String?,
    val quantity: String,
    val quantityError: String?,
) {
    companion object {
        val empty = CartItemEditing(
            id = null,
            name = "",
            nameError = null,
            price = "",
            priceError = null,
            quantity = "",
            quantityError = null,
        )

        fun fromCartItem(cartItem: CartItem): CartItemEditing {
            return CartItemEditing(
                id = cartItem.id,
                name = cartItem.name,
                nameError = null,
                price = cartItem.price.toString(),
                priceError = null,
                quantity = cartItem.quantity.toString(),
                quantityError = null,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheets(
    modalSheetState: SheetState,
    cartItem: CartItem? = null,
    onSave: (CartItem) -> Unit,
    onDelete: (CartItem) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var cartItemEditing by remember {
        mutableStateOf(cartItem?.let { CartItemEditing.fromCartItem(it) } ?: CartItemEditing.empty)
    }

    val name = cartItemEditing.name
    val price = cartItemEditing.price.toDoubleOrNull()
    val quantity = cartItemEditing.quantity.toIntOrNull()

    ModalBottomSheet(
        modifier = Modifier.wrapContentHeight(),
        onDismissRequest = onDismissRequest,
        sheetState = modalSheetState,
        dragHandle = { BottomSheetDefaults.ExpandedShape }) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                if (cartItem == null) {
                    "Add new item"
                } else {
                    "Update item"
                },
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = cartItemEditing.name,
                    onValueChange = { cartItemEditing = cartItemEditing.copy(name = it) },
                    label = { Text("Name") },
                    isError = name.isBlank(),
                    supportingText = {
                        if (name.isBlank()) {
                            Text("Name cannot be empty")
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = true,
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = cartItemEditing.price,
                    onValueChange = { cartItemEditing = cartItemEditing.copy(price = it) },
                    label = { Text("Price") },
                    isError = price == null,
                    supportingText = {
                        if (price == null) {
                            Text("Price must be a valid number")
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = cartItemEditing.quantity,
                    onValueChange = { cartItemEditing = cartItemEditing.copy(quantity = it) },
                    label = { Text("Initial Quantity") },
                    isError = quantity == null,
                    supportingText = {
                        if (quantity == null) {
                            Text("quantity must be a valid number")
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                )

                if (cartItem != null) {
                    OutlinedButton(
                        onClick = { onDelete(cartItem) },
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, Color.Red),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                    ) {
                        Text("Delete")
                    }
                }

                Button(
                    enabled = name.isNotBlank() && price != null && quantity != null,
                    onClick = {
                        if (name.isNotBlank() && price != null && quantity != null) {
                            onSave(
                                CartItem(
                                    id = cartItem?.id,
                                    name = name,
                                    price = price,
                                    quantity = quantity,
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        if (cartItem == null) {
                            "Add Item"
                        } else {
                            "Update Item"
                        }
                    )
                }
            }
        }

    }


}