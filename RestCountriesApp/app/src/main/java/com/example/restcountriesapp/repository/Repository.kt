package com.example.restcountriesapp.repository

import com.example.restcountriesapp.api.RetrofitInstance
import com.example.restcountriesapp.model.Country
import com.example.restcountriesapp.model.CountryResponse
import retrofit2.Response

class Repository {
    suspend fun getAllCountries(): Response<CountryResponse> = RetrofitInstance.api.getAllCountries()
    
}