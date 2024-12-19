package com.jfranco.multicounter.cart.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jfranco.multicounter.cart.entity.CartItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheets(
    modalSheetState: SheetState,
    cartItem: CartItem? = null,
    onSaved: (CartItem) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var itemName by remember {
        mutableStateOf(cartItem?.name ?: "")
    }
    var itemPrice by remember {
        mutableStateOf(cartItem?.price ?: 0.0)
    }
    var itemQuantity by remember {
        mutableStateOf(cartItem?.quantity ?: 0)
    }
    ModalBottomSheet(
        modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 1.5).dp),
        onDismissRequest = onDismissRequest,
        sheetState = modalSheetState,
        dragHandle = { BottomSheetDefaults.ExpandedShape }) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxHeight(),

            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                if (cartItem == null) {
                    "Add new item"
                } else {
                    "Update item"
                },
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )


            Column {
                Text("Item Name")
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Column {
                Text("Item Price")
                OutlinedTextField(
                    value = itemPrice.toString(),
                    onValueChange = { itemPrice = it.toDouble() },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Column {
                Text("Item Quantity")
                OutlinedTextField(
                    value = itemQuantity.toString(),
                    onValueChange = { itemQuantity = it.toInt() },
                    modifier = Modifier.fillMaxWidth(),
                )
            }


            Button(onClick = {
                val toSave =
                    cartItem?.copy(name = itemName, price = itemPrice, quantity = itemQuantity)
                        ?: CartItem(
                            name = itemName,
                            price = itemPrice,
                            quantity = itemQuantity
                        )
                onSaved(toSave)
            }, modifier = Modifier.fillMaxWidth()) {
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