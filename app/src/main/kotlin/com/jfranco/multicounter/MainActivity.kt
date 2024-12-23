package com.jfranco.multicounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jfranco.multicounter.feature.cart.ui.screen.CartScreen
import com.jfranco.multicounter.core.theme.MultiCounterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiCounterTheme {
                CartScreen()
            }
        }
    }
}
