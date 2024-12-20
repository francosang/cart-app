package com.jfranco.multicounter.cart.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
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
        }
    ) { state ->
        val items = state.items

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
        ) {
            if (state.itemBottomState is ItemBottomSheetState.Open) {
                CustomBottomSheets(
                    modalSheetState = modalSheetState, state.itemBottomState.item,
                    onDelete = { item ->
                        viewModel.action(Action.DeleteItem(item))
                    },
                    onSave = { item ->
                        viewModel.action(Action.SaveItem(item))
                    },
                    onDismissRequest = {
                        viewModel.action(Action.CloseBottomSheet)
                    },
                )
            }
            Box(
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        viewModel.action(Action.CreateItem)
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Icon")
                    Text("Add Item")
                }
            }
            if (items.isNotEmpty()) {
                LazyColumn(
                    reverseLayout = false,
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(items = items, key = { task -> task.id ?: 0 }) { task ->
                        AnimatedVisibility(
                            visible = true,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Box(Modifier.animateEnterExit(enter = scaleIn(), exit = scaleOut())) {
                                KartItem(
                                    task,
                                    onClick = {
                                        viewModel.action(Action.UpdateItem(task))
                                    },
                                )
                            }
                        }
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
fun KartItem(cartItem: CartItem, onClick: () -> Unit) {
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
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Remove, contentDescription = null)
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
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }

        }
    }
}