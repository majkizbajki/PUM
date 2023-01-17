package com.example.myfridge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.myfridge.data.model.Product
import com.example.myfridge.databinding.FragmentItemBinding
import com.example.myfridge.shoppinglist.ShoppingListViewModel
import com.example.myfridge.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemFragment : Fragment() {

    lateinit var binding: FragmentItemBinding
    val viewModel: ShoppingListViewModel by viewModels()
    var isEdit: Boolean = false
    var objProduct: Product? = null
    private val unitsMap = mapOf("pcs" to 0, "l" to 1, "ml" to 2, "g" to 3, "kg" to 4)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemBinding.inflate(layoutInflater)

        val spinner: Spinner = binding.spinner
        val spinnerAdapter = ArrayAdapter.createFromResource(requireActivity().applicationContext, R.array.units, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Do nothing
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing, first is always picked
            }

        }

        binding.backButton.setOnClickListener{
            val action: NavDirections
            if(arguments?.getString("navigation") == "shoppingList"){
                action = ItemFragmentDirections.actionItemFragmentToShopListFragment()
            } else {
                action = ItemFragmentDirections.actionItemFragmentToFridgeFragment()
            }

            Navigation.findNavController(binding.root).navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getString("navigation") == "shoppingList") {
            updateUI()
            binding.saveItemButton.setOnClickListener {
                if(isEdit){
                    updateItemFormShoppingList()
                } else {
                    addItemToShoppingList()
                }
            }
        }
    }

    private fun addItemToShoppingList(){
        if(validation()) {
            viewModel.addProductToShoppingList(
                Product(
                    id = "",
                    name = binding.productNameInput.text.toString(),
                    amount = binding.amountInput.text.toString(),
                    unit = binding.spinner.selectedItem.toString()
                )
            )
            val action = ItemFragmentDirections.actionItemFragmentToShopListFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }
        viewModel.addProductToShoppingList.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Loading -> {
                    binding.itemDetailsProgressBar.visibility = View.VISIBLE
                }
                is UiState.Failure -> {
                    binding.itemDetailsProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }
                is UiState.Success -> {
                    binding.itemDetailsProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), state.data, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateItemFormShoppingList(){
        if(validation()) {
            viewModel.updateProductFromShoppingList(
                Product(
                    id = objProduct?.id ?: "",
                    name = binding.productNameInput.text.toString(),
                    amount = binding.amountInput.text.toString(),
                    unit = binding.spinner.selectedItem.toString()
                )
            )
            val action = ItemFragmentDirections.actionItemFragmentToShopListFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }
        viewModel.updateProductFromShoppingList.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Loading -> {
                    binding.itemDetailsProgressBar.visibility = View.VISIBLE
                }
                is UiState.Failure -> {
                    binding.itemDetailsProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }
                is UiState.Success -> {
                    binding.itemDetailsProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), state.data, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateUI(){
        val type = arguments?.getString("type")
        val navigationType = arguments?.getString("navigation")
        if(navigationType == "shoppingList"){
            type.let {
                when(it){
                    "add" -> {
                        isEdit = false
                        binding.itemDetailsTitle.text = "Add product to shopping list"
                    }
                    "edit" -> {
                        isEdit = true
                        objProduct = arguments?.getParcelable("product")
                        binding.productNameInput.setText(objProduct?.name)
                        binding.amountInput.setText(objProduct?.amount)
                        binding.spinner.setSelection(unitsMap[objProduct?.unit]!!, true)
                        binding.itemDetailsTitle.text = "Edit product from shopping list"
                    }
                }
            }
            Log.d("TEST", "TEST")
        }
    }

    private fun validation(): Boolean{
        var isValid = true
        if(binding.productNameInput.text.isNullOrEmpty() || binding.amountInput.text.isNullOrEmpty()){
            isValid = false
            Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_LONG).show()
        }
        return isValid
    }

}