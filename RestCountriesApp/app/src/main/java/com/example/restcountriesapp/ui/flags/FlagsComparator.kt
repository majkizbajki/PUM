package com.example.restcountriesapp.ui.flags

import androidx.recyclerview.widget.DiffUtil
import com.example.restcountriesapp.model.Country

class FlagsComparator : DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(
        oldItem: Country,
        newItem: Country
    ): Boolean {
        return newItem.name == oldItem.name
    }

    override fun areContentsTheSame(
        oldItem: Country,
        newItem: Country
    ): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}