package com.appdot.io.wikipediademo.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*

import com.appdot.io.wikipediademo.R
import com.appdot.io.wikipediademo.WikiApplication
import com.appdot.io.wikipediademo.adapters.ArticleCardRecyclerAdapter
import com.appdot.io.wikipediademo.adapters.ArticleListItemRecyclerAdapter
import com.appdot.io.wikipediademo.managers.WikiManager
import com.appdot.io.wikipediademo.model.WikiPage
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : Fragment() {

    private var wikiManager: WikiManager? = null
    var historyRecycler: RecyclerView? = null
    private val adapter = ArticleListItemRecyclerAdapter()

    init {
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        historyRecycler = view.findViewById<RecyclerView>(R.id.history_article_recycler)

        historyRecycler!!.layoutManager = LinearLayoutManager(context)
        historyRecycler!!.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val history = wikiManager!!.getHistory()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(history as ArrayList<WikiPage>)
            activity?.runOnUiThread { adapter.notifyDataSetChanged() }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater!!.inflate(R.menu.history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_clear_history) {
            //show confirmation alert
            activity?.alert ("Are you sure want to clear your history?", "Confirm")
            { yesButton {
                // yes was hit...
                // clear history async
                    adapter.currentResults.clear()
                    doAsync{
                        wikiManager?.clearHistory()
                    }
                }
                noButton {

                }
            }?.show()
        }
        return true
    }

}
