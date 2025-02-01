package com.amb.stockmanagerapp.presentation.procuct_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.usecase.GetProductDetailsUseCase
import com.amb.stockmanagerapp.domain.usecase.SaveProductUseCase
import com.amb.stockmanagerapp.presentation.product_edit.ProductEditViewState
import com.amb.stockmanagerapp.presentation.ui.utils.Constants
import com.amb.stockmanagerapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetProductDetailsUseCase,
    private val saveProductUseCase: SaveProductUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ProductDetailsViewState())
    val viewState = _viewState.asStateFlow()

    private val _editViewState = MutableStateFlow(ProductEditViewState())
    val editViewState = _editViewState.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.PRODUCT_ID_KEY)?.let { id ->
            if (id.isNotEmpty()) {
                getProductsDetails(id)
            }
        }
    }

    private fun getProductsDetails(id: String) {
        viewModelScope.launch {
            useCase(id).collect { response ->
                when (response) {
                    is Response.Error -> {
                        _viewState.update { it.copy(isLoading = false, error = response.message) }
                    }

                    is Response.Loading -> {
                        _viewState.update { it.copy(isLoading = true) }
                    }

                    is Response.Success -> {
                        _viewState.update { it.copy(isLoading = false, data = response.data) }
                    }
                }
            }
        }
    }

    fun onSaveClick(product: Product) {
        val isNameValid = product.name.isNotEmpty()
        val isDescriptionValid = product.description.isNotEmpty()
        val isPriceValid = product.price >= 0.0
        _editViewState.update {
            ProductEditViewState(
                isNameValid = isNameValid,
                isDescriptionValid = isDescriptionValid,
                isPriceValid = isPriceValid
            )
        }
        if (isNameValid && isDescriptionValid && isPriceValid) {
            viewModelScope.launch {
                saveProductUseCase(product)
            }
        }
    }
}
