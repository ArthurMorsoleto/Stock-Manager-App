package com.amb.stockmanagerapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amb.stockmanagerapp.domain.usecase.GetProductsUseCase
import com.amb.stockmanagerapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val useCase: GetProductsUseCase
) : ViewModel() {

    private val _viewState = mutableStateOf(StockViewState())
    val viewState: State<StockViewState> = _viewState

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
                        StockViewState(
                            isLoading = false,
                            data = result.data ?: emptyList()
                        )
                    }

                    is Response.Loading -> {
                        StockViewState(isLoading = true)
                    }

                }
            }
        }
    }
}