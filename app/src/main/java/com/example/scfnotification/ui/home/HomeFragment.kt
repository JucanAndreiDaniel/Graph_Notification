package com.example.scfnotification.ui.home

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView;
//    private lateinit var adapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>
    private lateinit var currencyStringArray: Array<String>;

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        currencyStringArray = resources.getStringArray(R.array.countries_array);

        recyclerView = root.findViewById(R.id.rv_recyclerView);

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