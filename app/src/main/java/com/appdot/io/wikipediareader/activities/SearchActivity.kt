package com.appdot.io.wikipediareader.activities

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.appdot.io.wikipediareader.R
import com.appdot.io.wikipediareader.WikiApplication
import com.appdot.io.wikipediareader.adapters.ArticleListItemRecyclerAdapter
import com.appdot.io.wikipediareader.managers.WikiManager
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private var wikiManager: WikiManager? = null
    private var adapter: ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        wikiManager = (applicationContext as WikiApplication).wikiManager
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        search_results_recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        search_results_recycler.adapter = adapter
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home){
            finish()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu!!.findItem(R.id.action_search)

        val searchManager =  getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem!!.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                wikiManager?.search(query, 0,20,{wikiResult->
                    adapter.currentResults.clear()
                    adapter.currentResults.addAll(wikiResult.query!!.pages)
                    runOnUiThread{adapter.notifyDataSetChanged()}

                })
                return false
            }

        override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}
