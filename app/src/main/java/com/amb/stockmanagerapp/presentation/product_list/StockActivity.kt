package com.amb.stockmanagerapp.presentation.product_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amb.stockmanagerapp.presentation.Screen
import com.amb.stockmanagerapp.presentation.procuct_details.ProductDetails
import com.amb.stockmanagerapp.presentation.procuct_details.ProductDetailsViewModel
import com.amb.stockmanagerapp.presentation.product_edit.ProductEditScreen
import com.amb.stockmanagerapp.presentation.product_edit.ProductEditScreenMode
import com.amb.stockmanagerapp.presentation.ui.theme.StockManagerAppTheme
import com.amb.stockmanagerapp.presentation.ui.utils.Constants.PRODUCT_ID_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockManagerAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ProductList.route
                    ) {
                        composable(route = Screen.ProductList.route) {
                            val viewModel = hiltViewModel<StockViewModel>()
                            val state = viewModel.viewState.collectAsState().value

                            Column(
                                Modifier.padding(innerPadding)
                            ) {
                                TitleHeader(navController)
                                Filter { viewModel.onFilterUpdate(it) }
                                SortOptions(
                                    state = state,
                                    onNameSortClick = { viewModel.onNameSortClick() },
                                    onPriceSortClick = { viewModel.onPriceSortClick() }
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                                ItemList(state = state) { productId ->
                                    navController.navigate(
                                        route = Screen.ProductDetails.route + "/${productId}"
                                    )
                                }
                            }
                        }
                        composable(route = Screen.ProductDetails.route + "/{$PRODUCT_ID_KEY}") {
                            val viewModel = hiltViewModel<ProductDetailsViewModel>()
                            val state = viewModel.viewState.collectAsState().value
                            ProductDetails(navController, state)
                        }
                        composable(
                            route = Screen.ProductEdit.route + "?$PRODUCT_ID_KEY={$PRODUCT_ID_KEY}",
                            arguments = listOf(
                                navArgument(PRODUCT_ID_KEY) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) { content ->
                            val isEditMode = content.arguments?.getString(PRODUCT_ID_KEY) ?: ""
                            val mode = if (isEditMode.isNotEmpty()) {
                                ProductEditScreenMode.EDIT
                            } else {
                                ProductEditScreenMode.ADD
                            }
                            val viewModel = hiltViewModel<ProductDetailsViewModel>()
                            val state = viewModel.viewState.collectAsState().value
                            ProductEditScreen(navController, mode, state)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SortOptions(
        state: StockViewState,
        onNameSortClick: () -> Unit,
        onPriceSortClick: () -> Unit
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Row {
                SortBox(text = "name", sortBy = state.nameSorter) {
                    onNameSortClick.invoke()
                }
                SortBox(text = "price", sortBy = state.priceSorter) {
                    onPriceSortClick.invoke()
                }
            }
        }
    }

    @Composable
    private fun TitleHeader(navController: NavHostController) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart),
                text = "Stock",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(start = 16.dp),
                onClick = {
                    navController.navigate(Screen.ProductEdit.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null
                )
            }
        }
    }

    @Composable
    private fun Filter(onTextChange: (String) -> Unit) {
        var mutableText by remember { mutableStateOf(TextFieldValue("")) }
        return OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            value = mutableText,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            onValueChange = {
                mutableText = it
                onTextChange.invoke(mutableText.text)
            }
        )
    }

    @Composable
    private fun SortBox(text: String, sortBy: Boolean, onSortClick: () -> Unit) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Row(
                modifier = Modifier.clickable(
                    onClick = { onSortClick.invoke() }
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = if (sortBy) Icons.Default.KeyboardArrowDown
                    else Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        StockManagerAppTheme {
            ItemList(
                state = StockViewState(
                    isLoading = true
                )
            ) {}
        }
    }
}