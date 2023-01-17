package com.example.myfridge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfridge.databinding.FragmentFridgeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FridgeFragment : Fragment() {

    lateinit var binding: FragmentFridgeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFridgeBinding.inflate(layoutInflater)

        return binding.root
    }

}