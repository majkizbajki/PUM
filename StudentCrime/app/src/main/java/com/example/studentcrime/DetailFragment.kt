package com.example.studentcrime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        view.findViewById<Button>(R.id.back_to_list_button).setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToListFragment()
            Navigation.findNavController(view).navigate(action)
        }

        view.findViewById<TextView>(R.id.details_title_text).text = arguments?.getString("title")
        view.findViewById<TextView>(R.id.details_description_text).text = arguments?.getString("description")
        view.findViewById<TextView>(R.id.student_index_text).text = arguments?.getString("studentIndex")
        view.findViewById<TextView>(R.id.details_is_solved_text).text = arguments?.getString("isSolved")

        return view
    }

}