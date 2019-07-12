package com.example.newsone2.domain

// дописать параметры
class NewsObject(var id: Int = 1, var url: String = "", var imageUrl: String = "", var title: String = "",
                 var descrip: String = "", var byline: String = "", var copyright: String = "",
                 var date: String = "", var source: String = "", var favState: Boolean = false)