package com.example.HobbyArknight_160421080.model

data class News(
     val id: Int,
     var title: String,
     val username: String,
     val url: String,
     var desc: String,
     var pages: ArrayList<String>,
)
