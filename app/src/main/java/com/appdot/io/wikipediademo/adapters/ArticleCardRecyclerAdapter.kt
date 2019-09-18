package com.appdot.io.wikipediademo.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.appdot.io.wikipediademo.R
import com.appdot.io.wikipediademo.holders.CardHolder
import com.appdot.io.wikipediademo.holders.ListItemHolder

class ArticleCardRecyclerAdapter : RecyclerView.Adapter<CardHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_card_item, parent, false)
        return CardHolder(cardItem)
    }


    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {


    }

}
