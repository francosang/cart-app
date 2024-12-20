package com.jfranco.multicounter.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.jfranco.multicounter.core.state.Result
import kotlinx.coroutines.launch

@Composable
fun <STATE, ERROR> ScaffoldResultHandler(
    resultState: Result<STATE, ERROR>,
    topBar: @Composable () -> Unit = {},
    content: @Composable (STATE) -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = topBar,
    ) { innerPadding ->
        val state = when (resultState) {
            is Result.Err -> {
                val scope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Error: ${resultState.uuid}")
                    }
                }
                resultState.previous
            }

            is Result.Ok -> resultState.value
        }

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            content(state)
        }
    }
}