package com.jfranco.multicounter.cart.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfranco.multicounter.cart.entity.CartItem
import com.jfranco.multicounter.ui.ScaffoldResultHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(viewModel: CartViewModel = viewModel()) {
    val resultState by viewModel.state.collectAsState()
    val modalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ScaffoldResultHandler(
        resultState = resultState,
        topBar = {
            Surface(shadowElevation = 5.dp) {
                CenterAlignedTopAppBar(title = { Text("Cart") })
            }
        },
    ) { state ->
        val items = state.items

        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if (state.itemBottomState is ItemBottomSheetState.Open) {
                CustomBottomSheets(
                    modalSheetState = modalSheetState,
                    cartItem = state.itemBottomState.item,
                    onDelete = { item ->
                        viewModel.action(Action.DeleteItem(item))
                    },
                    onSave = { item ->
                        viewModel.action(Action.SaveItem(item))
                    },
                    onDismissRequest = {
                        viewModel.action(Action.CloseItemDetails)
                    },
                )
            }
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = {
                    viewModel.action(Action.CreateItem)
                },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Icon")
                Text("Add Item")
            }
            if (items.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(0.5F),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(items = items, key = { item -> item.id ?: 0 }) { item ->
                        AnimatedVisibility(
                            visible = true,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Box(Modifier.animateEnterExit(enter = scaleIn(), exit = scaleOut())) {
                                CartItem(
                                    item,
                                    onClick = {
                                        viewModel.action(Action.ViewItemDetails(item))
                                    },
                                    onIncrease = {
                                        viewModel.action(Action.IncrementQuantity(item))
                                    },
                                    onDecrease = {
                                        viewModel.action(Action.DecrementQuantity(item))
                                    }
                                )
                            }
                        }
                    }
                }
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                    Box(
                        Modifier.padding(16.dp),
                    ) {
                        Text(
                            text = "Total: $${state.total}",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    }

                }
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Insert a New Task")
                }
            }
        }
    }
}

@Composable
fun CartItem(
    cartItem: CartItem,
    onClick: () -> Unit,
    onIncrease: (CartItem) -> Unit,
    onDecrease: (CartItem) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = cartItem.name,
                    color = Color.Black,
                    style = TextStyle(fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Row {
                    Column(
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(
                            text = "$",
                            color = Color.Black,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "$",
                            color = Color.Black,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(
                            text = " ${cartItem.price}",
                            color = Color.Black,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = " ${cartItem.price * cartItem.quantity}",
                            color = Color.Black,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = " / unit",
                            color = Color.Black,
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Text(
                            text = " / total",
                            color = Color.Black,
                            style = TextStyle(fontSize = 14.sp)
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {

                AnimatedContent(
                    targetState = cartItem.quantity == 1,
                    label = "decrement animation"
                ) { isSingleItem ->
                    if (isSingleItem) {
                        IconButton(onClick = { onDecrease(cartItem) }) {
                            Icon(Icons.Default.Delete, tint = Color.Red, contentDescription = null)
                        }
                    } else {
                        IconButton(onClick = { onDecrease(cartItem) }) {
                            Icon(Icons.Default.Remove, contentDescription = null)
                        }
                    }
                }

                Box(
                    Modifier.defaultMinSize(minWidth = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cartItem.quantity.toString(),
                        color = Color.Black,
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
                IconButton(onClick = { onIncrease(cartItem) }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }

        }
    }
}