package com.example.HobbyArknight_160421080.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.HobbyArknight_160421080.databinding.FragmentHomeBinding
import com.example.HobbyArknight_160421080.model.News
import com.example.HobbyArknight_160421080.viewModel.NewsListViewModel


class HomeFragment : Fragment() {
    private lateinit var bind: FragmentHomeBinding
    private val news = arrayListOf<News>()
    private val adapter = NewsListAdapter(news)
    private lateinit var viewModel : NewsListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        viewModel.refresh()

        observeViewModel()

        bind.recyclerView.layoutManager = LinearLayoutManager(context)
        bind.recyclerView.adapter = adapter
    }

    fun observeViewModel(){
        viewModel.newsLD.observe(viewLifecycleOwner, Observer {
            Log.d("Data", it.toString())
            adapter.UpdateNews(it)
        })
    }

}