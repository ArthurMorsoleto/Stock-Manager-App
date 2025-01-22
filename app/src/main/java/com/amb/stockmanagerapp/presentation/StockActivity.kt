package com.amb.stockmanagerapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.amb.stockmanagerapp.presentation.ui.theme.StockManagerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockManagerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = hiltViewModel<StockViewModel>()
                    val state = viewModel.viewState.value
                    StockScreen(modifier = Modifier.padding(innerPadding), state)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        StockManagerAppTheme {
            StockScreen(
                state = StockViewState(
                    isLoading = true
                )
            )
        }
    }
}