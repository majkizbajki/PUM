package com.example.myfridge.data.repository

import com.example.myfridge.data.model.Product
import com.example.myfridge.util.UiState

interface ShoppingListRepository {

    fun getShoppingList(result: (UiState<List<Product>>) -> Unit)
    fun addProductToShoppingList(product: Product, result: (UiState<String>) -> Unit)
    fun updateProductFromShoppingList(product: Product, result: (UiState<String>) -> Unit)
    fun deleteProductFromShoppingList(product: Product, result: (UiState<String>) -> Unit)

    fun addProductToFridge(product: Product, result: (UiState<String>) -> Unit)
}