package com.scfnotification.notifyme.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scfnotification.notifyme.R
import com.scfnotification.notifyme.data.adapters.CoinWithValuesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CoinWithValuesAdapter
    private lateinit var homeSearchView: SearchView
    private var allCoins = false

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

        homeSearchView = root.findViewById(R.id.sv_searchView)
        homeSearchView.onActionViewCollapsed()
        homeSearchView.setOnClickListener { homeSearchView.isIconified = false }

        adapter = CoinWithValuesAdapter()
        recyclerView = root.findViewById(R.id.rv_recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(currentContext)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        homeViewModel.update(currentContext)
        showCoins(adapter)
        homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    homeViewModel.filter(query).observe(
                        viewLifecycleOwner,
                        {
                            if (it != null) {
                                allCoins = false
                                adapter.submitList(it)
                            }
                        }
                    )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null && newText.length > 2) {
                    homeViewModel.filter(newText).observe(
                        viewLifecycleOwner,
                        {
                            if (it != null) {
                                allCoins = false
                                adapter.submitList(it)
                            }
                        }
                    )
                } else {
                    if (!allCoins)
                        showCoins(adapter)
                }
                return false
            }
        })

        return root
    }

    private fun showCoins(adapter: CoinWithValuesAdapter) {
        allCoins = true
        homeViewModel.getCoins.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    adapter.submitList(it)
                }
            }
        )
    }

    override fun onResume() {
        homeSearchView.clearFocus()
        homeSearchView.onActionViewCollapsed()
        super.onResume()
    }
}
