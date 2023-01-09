package com.example.restcountriesapp.api

import com.example.restcountriesapp.model.CountryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CountryApi {
    @GET("all")
    suspend fun getAllCountries(): Response<CountryResponse>
}