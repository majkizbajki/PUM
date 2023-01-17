package com.example.myfridge.data.repository

import com.example.myfridge.data.model.Product
import com.example.myfridge.util.FireStoreTables
import com.example.myfridge.util.UiState
import com.google.firebase.firestore.FirebaseFirestore

class FridgeRepositoryImp(
    val database: FirebaseFirestore
): FridgeRepository {

    override fun getFridge(result: (UiState<List<Product>>) -> Unit) {
        database.collection(FireStoreTables.FRIDGE)
            .get()
            .addOnSuccessListener {
                val fridgeProducts = arrayListOf<Product>()
                for (document in it){
                    val product = document.toObject(Product::class.java)
                    fridgeProducts.add(product)
                }
                result.invoke(
                    UiState.Success(fridgeProducts)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }

    override fun updateProductFromFridge(
        product: Product,
        result: (UiState<String>) -> Unit
    ) {
        val document = database.collection(FireStoreTables.FRIDGE).document(product.id)
        document
            .set(product)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Product from fridge has been updated")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }

    override fun deleteProductFromFridge(
        product: Product,
        result: (UiState<String>) -> Unit
    ) {
        val document = database.collection(FireStoreTables.FRIDGE).document(product.id)
        document
            .delete()
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Product has been deleted from fridge")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }
}