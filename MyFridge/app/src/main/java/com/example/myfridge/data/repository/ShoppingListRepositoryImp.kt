package com.example.myfridge.data.repository

import com.example.myfridge.data.model.Product
import com.example.myfridge.util.FireStoreTables
import com.example.myfridge.util.UiState
import com.google.firebase.firestore.FirebaseFirestore

class ShoppingListRepositoryImp(
    val database: FirebaseFirestore
): ShoppingListRepository {

    override fun getShoppingList(result: (UiState<List<Product>>) -> Unit) {
        database.collection(FireStoreTables.SHOPPINGLIST)
            .get()
            .addOnSuccessListener {
                val shoppingProducts = arrayListOf<Product>()
                for (document in it){
                    val product = document.toObject(Product::class.java)
                    shoppingProducts.add(product)
                }
                result.invoke(
                    UiState.Success(shoppingProducts)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }

    override fun addProductToShoppingList(product: Product, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreTables.SHOPPINGLIST).document()
        product.id = document.id
        document
            .set(product)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Product has been added to shopping list")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }

    override fun updateProductFromShoppingList(
        product: Product,
        result: (UiState<String>) -> Unit
    ) {
        val document = database.collection(FireStoreTables.SHOPPINGLIST).document(product.id)
        document
            .set(product)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Product has been updated in shopping list")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }

    override fun deleteProductFromShoppingList(
        product: Product,
        result: (UiState<String>) -> Unit
    ) {
        val document = database.collection(FireStoreTables.SHOPPINGLIST).document(product.id)
        document
            .delete()
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Product has been deleted from shopping list")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }
}