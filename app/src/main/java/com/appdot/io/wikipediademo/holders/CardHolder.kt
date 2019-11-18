package com.appdot.io.wikipediademo.holders

import android.content.Intent
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.appdot.io.wikipediademo.R
import com.appdot.io.wikipediademo.activities.ArticleDetailActivity
import com.appdot.io.wikipediademo.model.WikiPage
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_detail.view.*

class CardHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private val articleImageView: ImageView = itemView.findViewById<ImageView>(R.id.article_image)
    private val titleTextView: TextView = itemView.findViewById<TextView>(R.id.article_title)
    private var currentPage: WikiPage? = null

    init {
        itemView.setOnClickListener{ view: View? ->

            var detailPageIntent = Intent(itemView.context, ArticleDetailActivity::class.java)
            var pageJson = Gson().toJson(currentPage)

            detailPageIntent.putExtra("page",pageJson)
            itemView.context.startActivity(detailPageIntent)
        }
    }

    fun updateWithPage(page:WikiPage){
        currentPage = page
        titleTextView.text = page.title

        //load image lazily with picasso
        if(page.thumbnail != null)
            Picasso.with(itemView.context).load(page.thumbnail!!.source)
                .into(articleImageView)
    }



}