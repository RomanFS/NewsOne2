package com.example.newsone2.domain

// дописать параметры
class NewsObject(var url: String = "", var imageUrl: String = "", var title: String = "",
                 var descrip: String = "", var byline: String = "", var copyright: String = "",
                 var date: String = "", var source: String = "", var favState: Boolean = false) {

    constructor(id: Int, url: String, imageUrl: String, title: String, descrip: String = "", byline: String = "",
                copyright: String, date: String, source: String, favState: Boolean) : this()
}