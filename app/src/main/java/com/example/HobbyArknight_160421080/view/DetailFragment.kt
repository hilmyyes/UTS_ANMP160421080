package com.example.HobbyArknight_160421080.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.example.HobbyArknight_160421080.databinding.FragmentDetailBinding
import com.example.HobbyArknight_160421080.viewModel.NewsListViewModel

class DetailFragment : Fragment() {
    private lateinit var viewModel: NewsListViewModel
    private lateinit var bind: FragmentDetailBinding
    private lateinit var id:Number
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return bind.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null) {
            id = DetailFragmentArgs.fromBundle(requireArguments()).idBerita
        }
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        viewModel.detail(id)

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.newsLD.observe(viewLifecycleOwner, Observer {
            Log.d("Data", it.toString())
            val news = it[0]
            bind.btnBack.isEnabled = false
            bind.txtTitle.text = news.title
            bind.txtUser.text = "@"+news.username

            var page = 0
            bind.txtDesc.text = news.pages[page]

            bind.btnNext.setOnClickListener {
                page++
                bind.btnBack.isEnabled = true
                Log.d("news pages",news.pages[page] )
                bind.txtDesc.text = news.pages[page]
                if(page >= news.pages.size-1){
                    bind.btnNext.isEnabled = false
                }
            }

            bind.btnBack.setOnClickListener {
                page--
                bind.btnNext.isEnabled = true
                bind.txtDesc.text = news.pages[page]
                if(page <= 0){
                    bind.btnBack.isEnabled = false
                }
            }


            val picasso = Picasso.Builder(bind.root.context)
            picasso.listener{picasso, uri, exception -> exception.printStackTrace()}
            picasso.build().load(news.url).into(bind.imgNews)

        })
    }
}