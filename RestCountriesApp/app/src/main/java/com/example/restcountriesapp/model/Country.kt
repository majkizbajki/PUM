package com.example.restcountriesapp.model

class CountryResponse : ArrayList<Country>()

data class Country (
    val name: Name,
    val capital: List<String>? = null,
    val flag: String,
)

data class Name(
    val official: String,
)