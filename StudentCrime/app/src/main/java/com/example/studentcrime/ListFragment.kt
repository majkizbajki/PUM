package com.example.studentcrime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {
    private lateinit var adapter : CrimeListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var crimeArrayList: ArrayList<Crime>

    lateinit var crimeTitle: Array<String>
    lateinit var crimeDescription: Array<String>
    lateinit var indexNumber: Array<Int>
    lateinit var isSolved: Array<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = CrimeListAdapter(crimeArrayList)
        recyclerView.adapter = adapter
    }

    private fun dataInitialize(){
        crimeArrayList = arrayListOf<Crime>()

        crimeTitle = arrayOf(
            "Crime #1",
            "Crime #2",
            "Crime #3",
            "Crime #4",
            "Crime #5",
            "Crime #6",
            "Crime #7",
            "Crime #8",
            "Crime #9",
            "Crime #10",
            "Crime #11",
            "Crime #12",
            "Crime #13",
            "Crime #14",
            "Crime #15",
            "Crime #16",
            "Crime #17",
            "Crime #18",
            "Crime #19",
            "Crime #20",
        )

        crimeDescription = arrayOf(
            "Description of crime number 1",
            "Description of crime number 2",
            "Description of crime number 3",
            "Description of crime number 4",
            "Description of crime number 5",
            "Description of crime number 6",
            "Description of crime number 7",
            "Description of crime number 8",
            "Description of crime number 9",
            "Description of crime number 10",
            "Description of crime number 11",
            "Description of crime number 12",
            "Description of crime number 13",
            "Description of crime number 14",
            "Description of crime number 15",
            "Description of crime number 16",
            "Description of crime number 17",
            "Description of crime number 18",
            "Description of crime number 19",
            "Description of crime number 20",
        )

        indexNumber = arrayOf(
            101,
            102,
            103,
            104,
            105,
            106,
            107,
            108,
            109,
            110,
            111,
            112,
            113,
            114,
            115,
            116,
            117,
            118,
            119,
            120,
        )

        isSolved = arrayOf(
            true,
            false,
            false,
            true,
            true,
            true,
            false,
            true,
            false,
            false,
            false,
            true,
            false,
            true,
            true,
            false,
            false,
            false,
            true,
            true,
        )

        for (i in crimeTitle.indices){
            val crime = Crime(i ,crimeTitle[i], crimeDescription[i], indexNumber[i], isSolved[i])
            crimeArrayList.add(crime)
        }
    }
}