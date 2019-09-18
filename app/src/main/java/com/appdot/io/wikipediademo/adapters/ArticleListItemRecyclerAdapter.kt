package com.appdot.io.wikipediademo.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.appdot.io.wikipediademo.R
import com.appdot.io.wikipediademo.holders.CardHolder
import com.appdot.io.wikipediademo.holders.ListItemHolder

class ArticleListItemRecyclerAdapter : RecyclerView.Adapter<ListItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)
        return ListItemHolder(cardItem)
    }


    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {


    }

}
