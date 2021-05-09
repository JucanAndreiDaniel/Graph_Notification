package com.example.scfnotification.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R
import com.example.scfnotification.data.adapters.BaseRecyclerAdapter
import com.example.scfnotification.data.adapters.CryptoCoinAdapter

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: CryptoCoinAdapter

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

        adapter = CryptoCoinAdapter(currentContext)
        recyclerView = root.findViewById(R.id.rv_recyclerView);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(currentContext)

        return root
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        val search = menu.findItem(R.id.nav_search)
//        val searchView = search?.actionView as SearchView
//
//        searchView.queryHint = "Search for something"
//
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.filter.filter(newText)
//                return true
//            }
//        })
//    }
}