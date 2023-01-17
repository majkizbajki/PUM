package com.example.myfridge.data.repository

import com.example.myfridge.data.model.Product
import com.example.myfridge.util.UiState

interface FridgeRepository {

    fun getFridge(result: (UiState<List<Product>>) -> Unit)
    fun updateProductFromFridge(product: Product, result: (UiState<String>) -> Unit)
    fun deleteProductFromFridge(product: Product, result: (UiState<String>) -> Unit)
}