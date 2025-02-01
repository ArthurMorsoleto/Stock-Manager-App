package com.amb.stockmanagerapp.presentation.product_edit

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.amb.stockmanagerapp.R
import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.presentation.procuct_details.ProductDetailsViewState
import com.amb.stockmanagerapp.presentation.ui.theme.StockManagerAppTheme
import com.amb.stockmanagerapp.presentation.ui.utils.components.Image
import com.amb.stockmanagerapp.presentation.ui.utils.components.rememberCurrencyVisualTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class ProductEditScreenMode {
    EDIT,
    ADD
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProductEditScreen(
    navController: NavHostController,
    mode: ProductEditScreenMode,
    state: ProductDetailsViewState,
    editState: ProductEditViewState,
    onSaveClick: (Product) -> Unit = {},
    onDeleteClick: (Product) -> Unit = {}
) {
    val context = LocalContext.current
    val currentProduct = remember { mutableStateOf(Product()) }
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Header(navController, mode)
            // TODO remove double Form
            if (mode == ProductEditScreenMode.EDIT) {
                state.data?.let {
                    currentProduct.value = it
                    Form(currentProduct)
                }
            } else {
                Form(currentProduct)
            }
            ProductImage(
                image = currentProduct.value.image, mode = mode
            ) {
                Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
            }
            SaveButton {
                onSaveClick.invoke(currentProduct.value)
                handleFormValidation(coroutineScope, editState, snackBarHostState)
            }
            if (mode == ProductEditScreenMode.EDIT) {
                DeleteButton { onDeleteClick.invoke(currentProduct.value) }
            }
        }
    }
}

fun handleFormValidation(
    coroutineScope: CoroutineScope,
    editState: ProductEditViewState,
    snackBarHostState: SnackbarHostState
) {
    coroutineScope.launch {
        editState.isNameValid?.let {
            if (it.not()) {
                snackBarHostState.showSnackbar(
                    message = "Product name must not be empty",
                    duration = SnackbarDuration.Short
                )
            }
        }
        editState.isDescriptionValid?.let {
            if (it.not()) {
                snackBarHostState.showSnackbar(
                    message = "Product description must not be empty",
                    duration = SnackbarDuration.Short
                )
            }
        }
        editState.isNameValid?.let {
            if (it.not()) {
                snackBarHostState.showSnackbar(
                    message = "Product price should be greater than zero",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}

@Composable
private fun Header(
    navController: NavHostController,
    mode: ProductEditScreenMode
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
        Text(
            text = if (mode == ProductEditScreenMode.ADD) {
                stringResource(R.string.new_product_title)
            } else {
                stringResource(R.string.edit_product_title)
            },
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            )
        )
    }
}

@Composable
private fun Form(product: MutableState<Product>) {
    InputText(
        value = product.value.name,
        label = stringResource(R.string.product_name_label),
        onTextChange = {
            product.value = product.value.copy(name = it)
        }
    )
    InputText(
        value = product.value.description,
        label = stringResource(R.string.product_description_label),
        onTextChange = { product.value = product.value.copy(description = it) }
    )
    InputText(
        value = if (product.value.price != 0.0) product.value.price.toString() else "",
        label = stringResource(R.string.product_price_label),
        keyboardType = KeyboardType.NumberPassword,
        maxLength = 9,
        transformation = rememberCurrencyVisualTransformation("USD"),
        onTextChange = {
            if (it.isNotEmpty()) {
                product.value = product.value.copy(price = it.toDouble())
            }
        }
    )
}

@Composable
private fun InputText(
    value: String = "",
    label: String,
    onTextChange: (String) -> Unit,
    maxLength: Int = 999,
    keyboardType: KeyboardType = KeyboardType.Text,
    transformation: VisualTransformation = VisualTransformation.None
) {
    var mutableText by remember { mutableStateOf(TextFieldValue(value)) } // TODO fix when first data is null
    return OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        value = mutableText,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        onValueChange = {
            if (mutableText.text.length <= maxLength) {
                mutableText = it
                onTextChange.invoke(mutableText.text)
            }
        },
        visualTransformation = transformation
    )
}

@Composable
private fun ProductImage(
    image: String,
    mode: ProductEditScreenMode,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .size(300.dp)
                .clickable { onClick.invoke() },
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.White,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.LightGray
            )
        ) {
            if (mode == ProductEditScreenMode.ADD) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(80.dp),
                        imageVector = Icons.Default.AddCircle, contentDescription = null,
                        tint = Color.Gray
                    )
                }
            } else {
                Image(image, modifier = Modifier.size(300.dp))
            }
        }
    }
}


@Composable
fun SaveButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 2.dp),
        onClick = { onClick() }
    ) {
        Text(stringResource(R.string.save_button))
        Spacer(modifier = Modifier.size(4.dp))
        Icon(imageVector = Icons.Default.Done, contentDescription = null)
    }
}

@Composable
fun DeleteButton(onClick: () -> Unit) {
    FilledTonalButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 2.dp),
        onClick = { onClick() }
    ) {
        Text(stringResource(R.string.delete_button))
        Spacer(modifier = Modifier.size(4.dp))
        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StockManagerAppTheme {
        ProductEditScreen(
            navController = rememberNavController(),
            mode = ProductEditScreenMode.ADD,
            state = ProductDetailsViewState(),
            editState = ProductEditViewState()
        )
    }
}