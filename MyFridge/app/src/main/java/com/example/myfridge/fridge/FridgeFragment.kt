package com.example.myfridge.fridge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myfridge.R
import com.example.myfridge.databinding.FragmentFridgeBinding
import com.example.myfridge.databinding.ProductListItemBinding
import com.example.myfridge.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FridgeFragment : Fragment() {

    private lateinit var binding: FragmentFridgeBinding
    private lateinit var productListItemBinding: ProductListItemBinding
    private val viewModel: FridgeViewModel by viewModels()
    private var deletePosition: Int = -1
    private val adapter by lazy {
        FridgeAdapter(
            onEditClicked = {pos, item ->
                findNavController().navigate(R.id.action_fridgeFragment_to_itemFragment, Bundle().apply {
                    putString("navigation", "fridge")
                    putString("type", "edit")
                    putParcelable("product", item)
                })
            },
            onDeleteClicked = {pos, item ->
                deletePosition = pos
                viewModel.deleteProductFromFridge(item)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFridgeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fridgeRecyclerView.adapter = adapter
        binding.fridgeRecyclerView.itemAnimator = null

        viewModel.getFridge()
        viewModel.fridge.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UiState.Failure -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }
                is UiState.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    adapter.updateList(state.data.toMutableList())
                    if(state.data.toMutableList().size == 0){
                        binding.emptyFridgeText.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.deleteProductFromFridge.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UiState.Failure -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }
                is UiState.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    if(deletePosition != -1){
                        adapter.removeItem(deletePosition)
                        if(adapter.itemCount == 0){
                            binding.emptyFridgeText.visibility = View.VISIBLE
                        }
                        deletePosition = -1
                    }
                }
            }
        }
    }

}