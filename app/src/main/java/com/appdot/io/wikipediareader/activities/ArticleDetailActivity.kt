package com.appdot.io.wikipediareader.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.appdot.io.wikipediareader.R
import com.appdot.io.wikipediareader.WikiApplication
import com.appdot.io.wikipediareader.managers.WikiManager
import com.appdot.io.wikipediareader.model.WikiPage
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
        // mListener = RecoverySystem.ProgressListener { progress: Int ->  }
        supportActionBar?.title = currentPage?.fullurl

        setCustomTabs()

        //webView = findViewById(R.id.article_detail_webView)
        var loadedPage : String = currentPage?.fullurl.toString()
        //webView.loadUrl(loadedPage)


     //   editText.setText(currentPage?.fullurl.toString())
        wikiManager?.addHistory(currentPage!!)



        /*webView.webViewClient = object: WebViewClient(){

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
               // progress_circular.visibility = View.VISIBLE
                return true
            }
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
               // progress_circular.visibility = View.VISIBLE
            }
            override fun onPageFinished(view: WebView?, url: String?) {
               // progress_circular.visibility = View.GONE
            }

        }
*/

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

    fun setCustomTabs(){
        val builder = CustomTabsIntent.Builder()
        //modify toolbar color
        builder.setToolbarColor(ContextCompat.getColor(this@ArticleDetailActivity, R.color.colorPrimary))
        // add share button to overflow menu
        builder.addDefaultShareMenuItem()
        // add menu item to overflow
        // builder.addMenuItem("MENU_ITEM_NAME", pendingIntent)
        // show website title
        builder.setShowTitle(true)

       // var icon: Bitmap = BitmapFactory.decodeResource(resources, android.R.drawable.save)

     //   Intent actionIntent=

       // builder.setActionButton(icon, "save", )

        //modify back button icon
        //builder.setCloseButtonIcon(bitmap)


        builder.setExitAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
        var uri : Uri = Uri.parse(currentPage?.fullurl.toString())
        // build custom tabs intent
        val customTabsIntent : CustomTabsIntent = builder.build()

        //launch the url
        customTabsIntent.launchUrl(this, uri)



    }

}