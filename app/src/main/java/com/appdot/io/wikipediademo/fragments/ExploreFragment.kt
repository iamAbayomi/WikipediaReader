package com.appdot.io.wikipediademo.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.appdot.io.wikipediademo.R
import com.appdot.io.wikipediademo.activities.SearchActivity
import com.appdot.io.wikipediademo.adapters.ArticleCardRecyclerAdapter
import com.appdot.io.wikipediademo.provider.ArticleDataProvider
import kotlinx.android.synthetic.main.fragment_explore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : Fragment() {
    private val articleProvider : ArticleDataProvider = ArticleDataProvider()
    var searchCardView: CardView? = null
    var exploreRecycler : RecyclerView? = null
    var adapter : ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()
    var refresher:  SwipeRefreshLayout? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater!!.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view.findViewById<CardView>(R.id.search_card_view)
        exploreRecycler = view.findViewById<RecyclerView>(R.id.explore_article_recycler)
        refresher = view.findViewById<SwipeRefreshLayout>(R.id.refresher)
        searchCardView!!.setOnClickListener{
            val searchIntent = Intent(context, SearchActivity::class.java )
            context!!.startActivity(searchIntent)
        }

        exploreRecycler!!.layoutManager = LinearLayoutManager(context)
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {

            getRandomArticles()
        }
        getRandomArticles()

       return view
    }

    private fun getRandomArticles(){
        refresher?.isRefreshing= true

        try {
            articleProvider.getRandom(15, { wikiResult ->
                adapter.currentResults.clear()
                adapter.currentResults.addAll(wikiResult.query!!.pages)
                activity?.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    refresher?.isRefreshing = false
                }

            })
        }
        catch(ex: Exception){
            //show alert
            var builder = AlertDialog.Builder(requireContext())
            builder.setMessage(ex.message).setTitle("oops")
            val dialog = builder.create()
            dialog.show()

        }
    }


}

