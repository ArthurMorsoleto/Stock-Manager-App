package com.amb.stockmanagerapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.domain.usecase.GetProductsUseCase
import com.amb.stockmanagerapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val useCase: GetProductsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(StockViewState())
    val viewState = _viewState.asStateFlow()

    private val _priceSorter = MutableStateFlow(true)
    val priceSorter = _priceSorter.asStateFlow()

    private val _nameSorter = MutableStateFlow(true)
    val nameSorter = _nameSorter.asStateFlow()

    private var products: List<Product> = listOf()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            useCase().collect { result ->
                _viewState.value = when (result) {
                    is Response.Error -> {
                        StockViewState(
                            isLoading = false,
                            error = result.message ?: "error"
                        )
                    }

                    is Response.Success -> {
                        products = result.data ?: emptyList()
                        StockViewState(
                            isLoading = false,
                            data = products
                        )
                    }

                    is Response.Loading -> {
                        StockViewState(isLoading = true)
                    }
                }
            }
        }
    }

    fun onPriceSortClick() {
        _priceSorter.update { it.not() }
        _viewState.update { value ->
            value.copy(
                isLoading = false,
                data = if (_priceSorter.value) {
                    value.data.sortedByDescending { it.price }
                } else {
                    value.data.sortedBy { it.price }
                }
            )
        }
    }

    fun onNameSortClick() {
        _nameSorter.update { it.not() }
        _viewState.update { value ->
            value.copy(
                isLoading = false,
                data = if (_nameSorter.value) {
                    value.data.sortedByDescending { it.name }
                } else {
                    value.data.sortedBy { it.name }
                }
            )
        }
    }

    fun onFilterUpdate(value: String) {
        _viewState.update { state ->
            state.copy(
                isLoading = false,
                data = products.filter { it.name.contains(value, ignoreCase = true) }
            )
        }
    }
}