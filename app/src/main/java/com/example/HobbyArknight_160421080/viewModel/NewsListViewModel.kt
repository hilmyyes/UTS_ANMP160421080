package com.example.HobbyArknight_160421080.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.HobbyArknight_160421080.model.News

class NewsListViewModel(application: Application): AndroidViewModel(application) {
    val newsLD = MutableLiveData<ArrayList<News>>()
    val newsErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        loadingLD.value = true
        newsErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/uts_anmp/news.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object: TypeToken<List<News>>() {}.type
                val result = Gson().fromJson<List<News>>(it, sType)
                newsLD.value = result as ArrayList<News>?
                loadingLD.value = false
                Log.d("showvolley", it)
            },
            {
                Log.d("showvolley", it.toString())
                newsErrorLD.value = false
                loadingLD.value = false
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun detail(id: Number){
        loadingLD.value = true
        newsErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/uts_anmp/news.php?id=$id"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object: TypeToken<ArrayList<News>>() {}.type
                val result = Gson().fromJson<ArrayList<News>>(it, sType)
                newsLD.value = result as ArrayList<News>
                loadingLD.value = false
                Log.d("showvolley", it)
            },
            {
                Log.d("showvolley", it.toString())
                newsErrorLD.value = false
                loadingLD.value = false
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}