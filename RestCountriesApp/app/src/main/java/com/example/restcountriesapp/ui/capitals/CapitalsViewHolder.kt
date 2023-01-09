package com.example.restcountriesapp.ui.capitals

import androidx.recyclerview.widget.RecyclerView
import com.example.restcountriesapp.databinding.CapitalListItemBinding
import com.example.restcountriesapp.databinding.FragmentCapitalsBinding
import com.example.restcountriesapp.model.Country

class CapitalsViewHolder(private val binding: CapitalListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Country){
        binding.capitalNameText.text = item.capital?.first()
        binding.capitalCountryNameText.text = item.name.official
    }
}