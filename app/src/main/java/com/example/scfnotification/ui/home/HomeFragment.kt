package com.example.scfnotification.ui.home

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R
import com.example.scfnotification.data.adapters.CoinWithValuesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CoinWithValuesAdapter
    private lateinit var homeSearchView: SearchView

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
        homeSearchView.isIconified = false

        adapter = CoinWithValuesAdapter(currentContext)
        recyclerView = root.findViewById(R.id.rv_recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(currentContext)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        homeViewModel.update(currentContext)
        subscribeUi(currentContext, adapter)

        homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    homeViewModel.filter(query).observe(
                        viewLifecycleOwner,
                        {
                            if (it != null) {
                                adapter.setCoinWithValuesList(it)
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
                                adapter.setCoinWithValuesList(it)
                            }
                        }
                    )
                }

                return false
            }
        })

        return root
    }

    private fun subscribeUi(context: Context, adapter: CoinWithValuesAdapter) {
        homeViewModel.getCoins.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    adapter.setCoinWithValuesList(it)
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
