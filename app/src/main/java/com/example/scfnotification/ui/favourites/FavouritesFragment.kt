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
import com.example.scfnotification.data.adapters.BaseRecyclerAdapter
import com.example.scfnotification.data.adapters.CryptoCoinAdapter
import com.example.scfnotification.data.daos.CryptoCoinDao
import com.example.scfnotification.data.repositories.CryptoCoinRepository
import com.example.scfnotification.data.retrofit.ApiInterface
import com.example.scfnotification.data.retrofit.Coin
import com.example.scfnotification.data.sharedpreferences.IPreferenceHelper
import com.example.scfnotification.data.sharedpreferences.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BaseRecyclerAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        lateinit var currentContext: Context


        val root = inflater.inflate(R.layout.fragment_favourites, container, false)
        if (container != null) {
            currentContext = container.context
        }
        val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(currentContext) }
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = BaseRecyclerAdapter(currentContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(currentContext)
        val apiInterface = ApiInterface.create().getFavs("Token "+preferenceHelper.getApiKey())
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
//        val response : List<Coin>? = homeViewModel.getList(apiInterface)

        return root
    }
}