package com.appdot.io.wikipediademo.activities

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import com.appdot.io.wikipediademo.R
import com.appdot.io.wikipediademo.WikiApplication
import com.appdot.io.wikipediademo.managers.WikiManager
import com.appdot.io.wikipediademo.model.WikiPage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_article_detail.*
import org.jetbrains.anko.toast

class ArticleDetailActivity : AppCompatActivity(){

    private var wikiManager: WikiManager? = null
    private var currentPage: WikiPage? = null
    private lateinit var webView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        wikiManager =(applicationContext as WikiApplication).wikiManager

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //get the page from the extras
        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

        supportActionBar?.title = currentPage?.fullurl

        webView = findViewById(R.id.article_detail_webView)

        webView.loadUrl(currentPage?.fullurl)


        wikiManager?.addHistory(currentPage!!)

        webView.webViewClient = object: WebViewClient(){

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                progress_circular.visibility = View.VISIBLE
                return true
            }
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progress_circular.visibility = View.VISIBLE
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                progress_circular.visibility = View.GONE
            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId== android.R.id.home) {
            finish()
        }
        else if (item!!.itemId == R.id.action_favourite) {
            try{
                //dtermine if article is already a favourite or not
                if(wikiManager!!.getIsFavourites(currentPage!!.pageid!!)){

                    wikiManager!!.removeFavourite(currentPage!!.pageid!!)
                    toast("Article removed from favourites")
                }
                else{
                    wikiManager!!.addFavourite(currentPage!!)
                    toast("Article added to Faourites")
                }
            }
            catch(ex: Exception){
                toast("Unable to update this article")
            }


        }

        return true

    }
}