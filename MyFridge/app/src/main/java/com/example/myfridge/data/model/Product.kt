package com.example.myfridge.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id: String= "",
    val name: String = "",
    val amount: String = "",
    val unit: String = ""
): Parcelable