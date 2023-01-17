package com.example.myfridge.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfridge.data.model.Product
import com.example.myfridge.data.repository.ShoppingListRepository
import com.example.myfridge.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    val repository: ShoppingListRepository
): ViewModel() {

    private val _shoppingList = MutableLiveData<UiState<List<Product>>>()
    val shoppingList: LiveData<UiState<List<Product>>>
        get() = _shoppingList

    private val _addProductToShoppingList = MutableLiveData<UiState<String>>()
    val addProductToShoppingList: LiveData<UiState<String>>
        get() = _addProductToShoppingList

    private val _updateProductFromShoppingList = MutableLiveData<UiState<String>>()
    val updateProductFromShoppingList: LiveData<UiState<String>>
        get() = _updateProductFromShoppingList

    private val _deleteProductFromShoppingList = MutableLiveData<UiState<String>>()
    val deleteProductFromShoppingList: LiveData<UiState<String>>
        get() = _deleteProductFromShoppingList

    private val _addProductToFridge = MutableLiveData<UiState<String>>()
    val addProductToFridge: LiveData<UiState<String>>
        get() = _addProductToFridge

    fun getShoppingList() {
        _shoppingList.value = UiState.Loading
        repository.getShoppingList {
            _shoppingList.value = it
        }
    }

    fun addProductToShoppingList(product: Product){
        _addProductToShoppingList.value = UiState.Loading
        repository.addProductToShoppingList(product) {
            _addProductToShoppingList.value = it
        }
    }

    fun updateProductFromShoppingList(product: Product){
        _updateProductFromShoppingList.value = UiState.Loading
        repository.updateProductFromShoppingList(product) {
            _updateProductFromShoppingList.value = it
        }
    }

    fun deleteProductFromShoppingList(product: Product) {
        _deleteProductFromShoppingList.value = UiState.Loading
        repository.deleteProductFromShoppingList(product) {
            _deleteProductFromShoppingList.value = it
        }
    }

    fun addProductToFridge(product: Product){
        _addProductToFridge.value = UiState.Loading
        repository.addProductToFridge(product) {
            _addProductToFridge.value = it
        }
    }

}