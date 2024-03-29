package com.appdot.io.wikipediareader.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*

import com.appdot.io.wikipediareader.R
import com.appdot.io.wikipediareader.WikiApplication
import com.appdot.io.wikipediareader.adapters.ArticleListItemRecyclerAdapter
import com.appdot.io.wikipediareader.managers.WikiManager
import com.appdot.io.wikipediareader.model.WikiPage
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : androidx.fragment.app.Fragment() {

    private var wikiManager: WikiManager? = null
    var historyRecycler: androidx.recyclerview.widget.RecyclerView? = null
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

        historyRecycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.history_article_recycler)

        historyRecycler!!.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
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
