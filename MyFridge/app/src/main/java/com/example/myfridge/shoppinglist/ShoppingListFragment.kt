package com.example.myfridge.shoppinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myfridge.R
import com.example.myfridge.data.model.Product
import com.example.myfridge.databinding.FragmentShopListBinding
import com.example.myfridge.fridge.FridgeAdapter
import com.example.myfridge.fridge.FridgeViewModel
import com.example.myfridge.itemDetails.ItemFragmentDirections
import com.example.myfridge.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingListFragment : Fragment() {

    private lateinit var binding: FragmentShopListBinding
    private val viewModel: ShoppingListViewModel by viewModels()
    private var deletePosition: Int = -1
    private val adapter by lazy {
        ShoppingListAdapter(
            onDoneClicked = {pos, item ->
                viewModel.addProductToFridge(
                    Product(
                        id = "",
                        name = item.name,
                        amount = item.amount,
                        unit = item.unit
                    )
                )
                deletePosition = pos
                viewModel.deleteProductFromShoppingList(item)
            },
            onEditClicked = {pos, item ->
                findNavController().navigate(R.id.action_shopListFragment_to_itemFragment, Bundle().apply {
                    putString("navigation", "shoppingList")
                    putString("type", "edit")
                    putParcelable("product", item)
                })
            },
            onDeleteClicked = {pos, item ->
                deletePosition = pos
                viewModel.deleteProductFromShoppingList(item)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shoppingListRecyclerView.adapter = adapter
        binding.shoppingListRecyclerView.itemAnimator = null

        binding.addButton.setOnClickListener {
            val args= Bundle()
            args.putString("navigation", "shoppingList")
            args.putString("type", "add")

            Navigation.findNavController(binding.root).navigate(R.id.action_shopListFragment_to_itemFragment, args)

        }

        viewModel.getShoppingList()
        viewModel.shoppingList.observe(viewLifecycleOwner){ state ->
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
                        binding.emptyShoppingList.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.addProductToFridge.observe(viewLifecycleOwner){ state ->
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
                }
            }
        }

        viewModel.deleteProductFromShoppingList.observe(viewLifecycleOwner){ state ->
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
                            binding.emptyShoppingList.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

}