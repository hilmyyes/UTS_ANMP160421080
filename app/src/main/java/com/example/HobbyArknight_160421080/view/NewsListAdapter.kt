package com.example.HobbyArknight_160421080.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.HobbyArknight_160421080.databinding.NewsListItemBinding
import com.example.HobbyArknight_160421080.model.News

class NewsListAdapter(val NewsList: ArrayList<News>): RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {
    class NewsViewHolder(val bind: NewsListItemBinding): RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val bind = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(bind)
    }

    override fun getItemCount(): Int {
        return NewsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind.txtTitle.text = NewsList[position].title
        holder.bind.txtUser.text = "@"+NewsList[position].username
        holder.bind.txtDesc.text = NewsList[position].desc
        val id = NewsList[position].id

        val picasso = Picasso.Builder(holder.bind.root.context)
        picasso.listener{picasso, uri, exception -> exception.printStackTrace()}
        picasso.build().load(NewsList[position].url).into(holder.bind.imgNews)

        holder.bind.btnDetail.setOnClickListener {
            val action = HomeFragmentDirections.detailFragmentAction(id)
            Navigation.findNavController(it).navigate(action)
        }

    }

    fun UpdateNews(newNewsList: ArrayList<News>){
        NewsList.clear()
        NewsList.addAll(newNewsList)
        notifyDataSetChanged()
    }
}