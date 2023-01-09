package com.example.restcountriesapp.ui.capitals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restcountriesapp.R
import com.example.restcountriesapp.databinding.CapitalListItemBinding
import com.example.restcountriesapp.model.Country

class CapitalsAdapter(itemComparator: CapitalsComparator)
    : ListAdapter<Country, CapitalsViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapitalsViewHolder {
        return CapitalsViewHolder(
            CapitalListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CapitalsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}