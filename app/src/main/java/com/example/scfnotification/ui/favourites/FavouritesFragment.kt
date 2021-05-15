package com.example.scfnotification.ui.favourites

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.scfnotification.R
import com.example.scfnotification.data.adapters.CoinWithValuesAdapter

class FavouritesFragment : Fragment() {

    private lateinit var favouritesViewModel: FavouritesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CoinWithValuesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lateinit var currentContext: Context

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        if (container != null) {
            currentContext = container.context
        }
        adapter = CoinWithValuesAdapter(currentContext)
        recyclerView = root.findViewById(R.id.fav_recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(currentContext)

        favouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
        favouritesViewModel.update(currentContext)
        favouritesViewModel.getFavorites(currentContext).observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    adapter.setCoinWithValuesList(it)
                }
            }
        )
        val itemsswipetorefresh = root.findViewById<SwipeRefreshLayout>(R.id.fav_swipe)
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                currentContext,
                R.color.light_orange
            )
        )
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        itemsswipetorefresh.setOnRefreshListener {
            favouritesViewModel.update(currentContext)
        }
        return root
    }
}
//        val root = inflater.inflate(R.layout.fragment_favourites, container, false)
//
//        val currentContext = requireContext()
//        adapter = CoinWithValuesAdapter(currentContext)
//        recyclerView = root.findViewById(R.id.rv_recyclerView)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(currentContext)
//
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(currentContext)
//       
//        favouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
//
//        favouritesViewModel.getFavorites(currentContext)
//        // ** Set the colors of the Pull To Refresh View
//        
//        
//
//        return root
//    }
// }
