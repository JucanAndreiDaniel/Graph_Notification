package com.example.scfnotification.ui.favourites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R
import com.example.scfnotification.data.adapters.CryptoCoinAdapter
import com.example.scfnotification.data.daos.CryptoCoinDao
import com.example.scfnotification.data.repositories.CryptoCoinRepository

class FavouritesFragment : Fragment() {

    private lateinit var favouritesViewModel: FavouritesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        lateinit var currentContext: Context

//        favouritesViewModel =
//                ViewModelProvider(this,FavoriteViewModelFactory()).get(FavouritesViewModel::class.java)
//        fab.setOnClickListener {  }

        val root = inflater.inflate(R.layout.fragment_favourites, container, false)
        if (container != null) {
            currentContext = container.context
        }
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CryptoCoinAdapter(currentContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(currentContext)

        return root
    }
}