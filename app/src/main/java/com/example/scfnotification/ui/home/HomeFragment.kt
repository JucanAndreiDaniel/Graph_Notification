package com.example.scfnotification.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scfnotification.R
import com.example.scfnotification.data.adapters.BaseRecyclerAdapter
import com.example.scfnotification.data.retrofit.ApiInterface
import com.example.scfnotification.data.retrofit.Coin
import com.example.scfnotification.data.sharedpreferences.IPreferenceHelper
import com.example.scfnotification.data.sharedpreferences.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BaseRecyclerAdapter


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
        val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(currentContext) }

        adapter = BaseRecyclerAdapter(currentContext)
        recyclerView = root.findViewById(R.id.rv_recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(currentContext)

        val apiInterface = ApiInterface.create().getCoins("Token "+preferenceHelper.getApiKey())
        var responseBody: List<Coin> = listOf()
        apiInterface.enqueue( object : Callback<List<Coin>> {
            override fun onResponse(call: Call<List<Coin>>?, response: Response<List<Coin>>?) {

                if(response!!.isSuccessful) {
                    responseBody = response.body()!!
                    adapter.setCoinListItems(responseBody)
                }

            }

            override fun onFailure(call: Call<List<Coin>>?, t: Throwable?) {
            }
        })
        Log.d("test", "onResponse: $responseBody")

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