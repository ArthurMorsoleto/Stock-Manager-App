package com.amb.stockmanagerapp.presentation

sealed class Screen(val route: String) {
    data object ProductList : Screen("product_list_screen")
    data object ProductDetails : Screen("product_details_screen")
    data object ProductEdit : Screen("product_edit_screen")
}