package com.example.restcountriesapp.ui.flags

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restcountriesapp.model.CountryResponse
import com.example.restcountriesapp.repository.Repository
import com.example.restcountriesapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class FlagsViewModel: ViewModel() {
    private val repository = Repository()
    private val _countryList: MutableLiveData<Resource<CountryResponse>> = MutableLiveData()
    val countryList: LiveData<Resource<CountryResponse>>
        get() = _countryList

    fun getAllCountries() = viewModelScope.launch {
        _countryList.postValue(Resource.Loading())
        val response = repository.getAllCountries()
        _countryList.postValue(handleCountryListResponse(response))
    }

    private fun handleCountryListResponse(response: Response<CountryResponse>)
            : Resource<CountryResponse> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(response.message())
    }
}