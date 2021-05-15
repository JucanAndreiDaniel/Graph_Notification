package com.example.scfnotification.ui.favourites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R
import com.example.scfnotification.data.adapters.CoinWithValuesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
//        favouritesViewModel.update(currentContext)
        favouritesViewModel.getFavorites.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    adapter.setCoinWithValuesList(it)
                }
            }
        )
//        val itemsSwipeRefresh = root.findViewById<SwipeRefreshLayout>(R.id.fav_swipe)
//        itemsSwipeRefresh.setProgressBackgroundColorSchemeColor(
//            ContextCompat.getColor(
//                currentContext,
//                R.color.light_orange
//            )
//        )
//        itemsSwipeRefresh.setColorSchemeColors(Color.WHITE)
//        itemsSwipeRefresh.setOnRefreshListener {
//            favouritesViewModel.update(currentContext)
//        }
        return root
    }
}
