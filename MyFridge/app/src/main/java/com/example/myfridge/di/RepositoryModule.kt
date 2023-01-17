package com.example.myfridge.di

import com.example.myfridge.data.repository.ShoppingListRepository
import com.example.myfridge.data.repository.ShoppingListRepositoryImp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideShoppingListRepository(
        database: FirebaseFirestore
    ): ShoppingListRepository{
        return ShoppingListRepositoryImp(database)
    }
}