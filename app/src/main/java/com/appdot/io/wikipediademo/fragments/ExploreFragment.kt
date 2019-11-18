package com.appdot.io.wikipediademo.fragments


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.appdot.io.wikipediademo.R
import com.appdot.io.wikipediademo.WikiApplication
import com.appdot.io.wikipediademo.activities.SearchActivity
import com.appdot.io.wikipediademo.adapters.ArticleCardRecyclerAdapter
import com.appdot.io.wikipediademo.managers.WikiManager
import com.appdot.io.wikipediademo.provider.ArticleDataProvider
import com.appdot.io.wikipediademo.receivers.MyBroadcastReceiver
import kotlinx.android.synthetic.main.fragment_explore.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : androidx.fragment.app.Fragment() {
    private var wikiManager: WikiManager? = null
    var searchCardView: CardView? = null
    var exploreRecycler : RecyclerView? = null
    var adapter : ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()
    var refresher:  SwipeRefreshLayout? =null

    var isConnected : Boolean = false

    companion object{
        val info: Boolean = false
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view.findViewById(R.id.search_card_view)
        exploreRecycler = view.findViewById(R.id.explore_article_recycler)
        refresher = view.findViewById(R.id.refresher)

        searchCardView!!.setOnClickListener{
            activity.let {
             val searchIntent = Intent (it, SearchActivity::class.java)
                it?.startActivity(searchIntent)

            }
        }

        exploreRecycler!!.layoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager(
            3,
            androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
        )
       //exploreRecycler!!.layoutManager = LinearLayoutManager(context)
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {
            getRandomArticles()
        }

        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        isConnected = activeNetwork?.isConnected == true


        getRandomArticles()

       return view
    }

    private fun getRandomArticles() {

        if (isConnected) {
            refresher?.isRefreshing = true
            try {
                wikiManager?.getRandom(30, { wikiResult ->
                    adapter.currentResults.clear()
                    adapter.currentResults.addAll(wikiResult.query!!.pages)
                    activity?.runOnUiThread {
                        adapter.notifyDataSetChanged()
                        refresher?.isRefreshing = false
                    }
                })
            } catch (ex: Exception) {
                //show alert
                var builder = AlertDialog.Builder(requireContext())
                builder.setMessage(ex.message).setTitle("oops")
                val dialog = builder.create()
                dialog.show()
            }
        }
    }
}

