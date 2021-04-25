package com.example.scfnotification.ui.home

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.scfnotification.R

class HomeFragment : Fragment() {

    private lateinit var adapter: ArrayAdapter<*>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

//        val BitcoinArray = arrayOf("Bitcoin", "eth", "polka")

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.countries_array))

        val listView = root.requireViewById<ListView>(R.id.lv_listView)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity?.applicationContext, parent?.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
        }

        val emptyTextView = root.requireViewById<TextView>(R.id.tv_emptyTextView)

        listView.emptyView = emptyTextView

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val search = menu.findItem(R.id.nav_search)
        val searchView = search?.actionView as SearchView

        searchView.queryHint = "Search for something"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }
}