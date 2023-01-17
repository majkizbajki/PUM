package com.example.myfridge.fridge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfridge.data.model.Product
import com.example.myfridge.data.repository.FridgeRepository
import com.example.myfridge.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FridgeViewModel @Inject constructor(
    val repository: FridgeRepository
): ViewModel() {

    private val _fridge = MutableLiveData<UiState<List<Product>>>()
    val fridge: LiveData<UiState<List<Product>>>
        get() = _fridge

    private val _updateProductFromFridge = MutableLiveData<UiState<String>>()
    val updateProductFromFridge: LiveData<UiState<String>>
        get() = _updateProductFromFridge

    private val _deleteProductFromFridge = MutableLiveData<UiState<String>>()
    val deleteProductFromFridge: LiveData<UiState<String>>
        get() = _deleteProductFromFridge

    fun getFridge() {
        _fridge.value = UiState.Loading
        repository.getFridge {
            _fridge.value = it
        }
    }

    fun updateProductFromFridge(product: Product){
        _updateProductFromFridge.value = UiState.Loading
        repository.updateProductFromFridge(product) {
            _updateProductFromFridge.value = it
        }
    }

    fun deleteProductFromFridge(product: Product) {
        _deleteProductFromFridge.value = UiState.Loading
        repository.deleteProductFromFridge(product) {
            _deleteProductFromFridge.value = it
        }
    }

}