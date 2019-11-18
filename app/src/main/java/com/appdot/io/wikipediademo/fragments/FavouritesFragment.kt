package com.appdot.io.wikipediademo.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.appdot.io.wikipediademo.R
import com.appdot.io.wikipediademo.WikiApplication
import com.appdot.io.wikipediademo.adapters.ArticleCardRecyclerAdapter
import com.appdot.io.wikipediademo.adapters.ArticleListItemRecyclerAdapter
import com.appdot.io.wikipediademo.managers.WikiManager
import com.appdot.io.wikipediademo.model.WikiPage
import kotlinx.android.synthetic.main.fragment_favourite.*
import org.jetbrains.anko.doAsync

/**
 * A simple [Fragment] subclass.
 *
 */
class FavouritesFragment : androidx.fragment.app.Fragment() {

    private var wikiManager: WikiManager? = null
    var favouritesRecycler: androidx.recyclerview.widget.RecyclerView? = null
    private val adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val  view = inflater.inflate(R.layout.fragment_favourite, container, false)

        favouritesRecycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.favourites_article_recycler)
        favouritesRecycler!!.layoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager(
            2,
            androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
        )
        favouritesRecycler!!.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        doAsync{
            val favouriteArticles = wikiManager!!.getFavourites()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(favouriteArticles as ArrayList<WikiPage>)
            activity?.runOnUiThread{adapter.notifyDataSetChanged()}
        }
    }

}
