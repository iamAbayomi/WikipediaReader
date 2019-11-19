package com.appdot.io.wikipediareader.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.appdot.io.wikipediareader.R
import com.appdot.io.wikipediareader.holders.CardHolder
import com.appdot.io.wikipediareader.model.WikiPage

class ArticleCardRecyclerAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<CardHolder>(){

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
