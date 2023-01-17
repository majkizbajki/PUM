package com.example.myfridge.shoppinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.data.model.Product
import com.example.myfridge.databinding.ProductListItemBinding

class ShoppingListAdapter(
    val onEditClicked: (Int, Product) -> Unit,
    val onDeleteClicked: (Int, Product) -> Unit
): RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder>() {

    private var list: MutableList<Product> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    inner class MyViewHolder(val binding: ProductListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product){
            binding.productNameText.text = item.name
            binding.productAmountWithUnitText.text = "${item.amount} ${item.unit}"
            binding.editProductButton.setOnClickListener {
                onEditClicked.invoke(absoluteAdapterPosition, item)
            }
            binding.deleteProductButton.setOnClickListener {
                onDeleteClicked.invoke(absoluteAdapterPosition, item)
            }
        }
    }

    fun updateList(list: MutableList<Product>){
        this.list = list
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        list.removeAt(position)
        notifyItemChanged(position)
    }

}