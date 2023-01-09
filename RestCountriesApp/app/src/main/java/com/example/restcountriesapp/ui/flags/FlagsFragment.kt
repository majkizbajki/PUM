package com.example.restcountriesapp.ui.flags

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restcountriesapp.databinding.FragmentFlagsBinding
import com.example.restcountriesapp.model.Country
import com.example.restcountriesapp.ui.capitals.CapitalsAdapter
import com.example.restcountriesapp.util.Resource


class FlagsFragment : Fragment() {
    private val flagsViewModel: FlagsViewModel by viewModels()
    private lateinit var binding: FragmentFlagsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFlagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flagsViewModel.getAllCountries()

        val adapter = FlagsAdapter(FlagsComparator())
        setupRecyclerView(adapter)

        observeCountries(adapter)
    }

    private fun setupRecyclerView(flagsAdapter: FlagsAdapter) {
        binding.flagsRecyclerView.apply {
            adapter = flagsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeCountries(flagsAdapter: FlagsAdapter) {
        flagsViewModel.countryList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { res ->
                        Log.d("RESPONSE_OK", res.toString())
                        flagsAdapter.submitList(res.map { Country(it.name, it.capital, it.flag) })
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