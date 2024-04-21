package com.example.HobbyArknight_160421080.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.HobbyArknight_160421080.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserViewModel(application: Application): AndroidViewModel(application) {
    val userLD = MutableLiveData<User>()
    var successLD = MutableLiveData<Boolean>()
    val userErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

//    fun checkUsername(user: String){
//        loadingLD.value = true
//        userErrorLD.value = false
//
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/uts_anmp/cekUsername.php"
//    }

    fun signin(user: String, pass: String) {
        loadingLD.value = true
        userErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/uts_anmp/login.php?username=$user&password=$pass"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object: TypeToken<User>() {}.type
                val result = Gson().fromJson<User>(it, sType)
                userLD.value = result
                loadingLD.value = false
                Log.d("showvolley", it)
            },
            {
                Log.d("showvolley", it.toString())
                userErrorLD.value = true
                loadingLD.value = false
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun register(user: User){
        loadingLD.value = true
        userErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/uts_anmp/register.php?username=${user.username}&password=${user.password}&depan=${user.nama_depan}&belakang=${user.nama_belakang}&email=${user.email}"
        Log.d("url", url)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object: TypeToken<Boolean>() {}.type
                val result = Gson().fromJson<Boolean>(it, sType)
                successLD.value = result
                loadingLD.value = false
                Log.d("showvolley", it)
            },
            {
                Log.d("showvolley", it.toString())
                userErrorLD.value = true
                loadingLD.value = false
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun update(user: User){
        loadingLD.value = true
        userErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/uts_anmp/update.php?username=${user.username}&newpass=${user.password}&depan=${user.nama_depan}&belakang=${user.nama_belakang}"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object: TypeToken<Boolean>() {}.type
                val result = Gson().fromJson<Boolean>(it, sType)
                successLD.value = result
                loadingLD.value = false
                Log.d("showvolley", it)
            },
            {
                Log.d("showvolley", it.toString())
                userErrorLD.value = true
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