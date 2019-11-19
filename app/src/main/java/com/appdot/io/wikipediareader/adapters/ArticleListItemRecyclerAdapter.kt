package com.appdot.io.wikipediareader.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.appdot.io.wikipediareader.R
import com.appdot.io.wikipediareader.holders.ListItemHolder
import com.appdot.io.wikipediareader.model.WikiPage

class ArticleListItemRecyclerAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<ListItemHolder>(){

    val currentResults : ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)
        return ListItemHolder(cardItem)
    }


    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        var page = currentResults[position]
        holder?.updateWithPage(page)
    }





}
