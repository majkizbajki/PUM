package com.example.restcountriesapp.ui.capitals

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restcountriesapp.R
import com.example.restcountriesapp.databinding.FragmentCapitalsBinding
import com.example.restcountriesapp.model.Country
import com.example.restcountriesapp.repository.Repository
import com.example.restcountriesapp.util.Resource

class CapitalsFragment : Fragment() {

    private val capitalsViewModel: CapitalsViewModel by viewModels()
    private lateinit var binding: FragmentCapitalsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCapitalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        capitalsViewModel.getAllCountries()

        val adapter = CapitalsAdapter(CapitalsComparator())
        setupRecyclerView(adapter)

        observeCountries(adapter)
    }

    private fun setupRecyclerView(capitalsAdapter: CapitalsAdapter) {
        binding.capitalsRecyclerView.apply {
            adapter = capitalsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeCountries(capitalsAdapter: CapitalsAdapter) {
        capitalsViewModel.countryList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { res ->
                    Log.d("RESPONSE_OK", res.toString())
                    capitalsAdapter.submitList(res.map { Country(it.name, it.capital, it.flag) })
                    }
                }
                is Resource.Error -> {
                    response.message?.let { Log.e("RESPONSE_ERROR", "Error occurred: $it") }
                }
                is Resource.Loading -> Log.d("RESPONSE_LOADING", "LOADING DATA")
            }
        }
    }

}