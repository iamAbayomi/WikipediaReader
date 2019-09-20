package com.appdot.io.wikipediademo.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.appdot.io.wikipediademo.R
import com.appdot.io.wikipediademo.holders.CardHolder
import com.appdot.io.wikipediademo.holders.ListItemHolder
import com.appdot.io.wikipediademo.model.WikiPage

class ArticleCardRecyclerAdapter : RecyclerView.Adapter<CardHolder>(){

    val currentResults: ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_card_item, parent, false)
        return CardHolder(cardItem)
    }


    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        var page = currentResults[position]
        holder?.updateWithPage(page)

    }

}
