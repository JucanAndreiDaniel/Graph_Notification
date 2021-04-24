package com.example.scfnotification.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.scfnotification.R

class HomeFragment : Fragment() {

    private lateinit var adapter: ArrayAdapter<*>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.countries_array))

        lv_listView.adapter = adapter

        return root
    }
}