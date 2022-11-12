package com.example.studentcrime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class CrimeListAdapter(private val crimesList: ArrayList<Crime>) : RecyclerView.Adapter<CrimeListAdapter.CrimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.crime_list_item, parent, false)
        return CrimeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CrimeViewHolder, position: Int) {
        val currentItem = crimesList[position]
        holder.crimeImage.visibility = if(currentItem.isSolved) View.VISIBLE else View.INVISIBLE
        holder.crimeTitle.text = currentItem.crimeTitle
    }

    override fun getItemCount(): Int {
        return crimesList.size
    }

    inner class CrimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val crimeImage : ImageView = itemView.findViewById(R.id.crimeImage)
        val crimeTitle : TextView = itemView.findViewById(R.id.crimeTitle)

        init {
            itemView.setOnClickListener{
                val args = Bundle()
                args.putString("title", crimesList[adapterPosition].crimeTitle)
                args.putString("description", crimesList[adapterPosition].crimeDescription)
                args.putString("studentIndex", "Student's index: ${crimesList[adapterPosition].indexNumber}")
                args.putString("isSolved", if(crimesList[adapterPosition].isSolved) "Solved" else "Not solved")

                Navigation.findNavController(itemView).navigate(R.id.action_listFragment_to_detailFragment, args)
            }
        }
    }
}