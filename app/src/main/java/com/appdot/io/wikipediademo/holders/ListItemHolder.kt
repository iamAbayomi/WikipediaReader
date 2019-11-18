package com.appdot.io.wikipediademo.holders

import android.content.Intent
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

class ListItemHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private val articleImageView: ImageView = itemView.findViewById<ImageView>(R.id.result_icon)
    private val titleTextView: TextView = itemView.findViewById<TextView>(R.id.result_title)
    private var currentPage : WikiPage? = null
    init {
        itemView.setOnClickListener{ view: View? ->

            var detailPageIntent = Intent(itemView.context, ArticleDetailActivity::class.java)
            var pageJson = Gson().toJson(currentPage)

            detailPageIntent.putExtra("page",pageJson)
            itemView.context.startActivity(detailPageIntent)
        }
    }

    fun updateWithPage(page: WikiPage){
        if(page.thumbnail != null)
            Picasso.with(itemView.context).load(page.thumbnail!!.source)
                .into(articleImageView)

        titleTextView.text = page.title

        currentPage = page
    }


}