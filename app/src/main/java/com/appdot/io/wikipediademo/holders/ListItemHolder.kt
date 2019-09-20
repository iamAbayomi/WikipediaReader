package com.appdot.io.wikipediademo.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.appdot.io.wikipediademo.R
import com.appdot.io.wikipediademo.model.WikiPage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_detail.view.*

class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val articleImageView: ImageView = itemView.findViewById<ImageView>(R.id.result_icon)
    private val titleTextView: TextView = itemView.findViewById<TextView>(R.id.result_title)

    private var currentPage : WikiPage? = null

    fun updateWithPage(page: WikiPage){
        if(page.thumbnail != null)
            Picasso.with(itemView.context).load(page.thumbnail!!.source)
                .into(articleImageView)

        titleTextView.text = page.title

        currentPage = page
    }


}