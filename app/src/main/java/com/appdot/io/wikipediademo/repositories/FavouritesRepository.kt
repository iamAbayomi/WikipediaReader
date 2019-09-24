package com.appdot.io.wikipediademo.repositories


import com.appdot.io.wikipediademo.model.WikiPage
import com.appdot.io.wikipediademo.model.WikiThumbnail
import com.google.gson.Gson
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser

class FavouritesRepository(val databaseHelper: ArticleDatabaseOpenHelper){
    private val TABLE_NAME : String = "Favourites"

    fun addFavourites(page: WikiPage){
        databaseHelper.use{
            insert(TABLE_NAME,
                "id"  to page.pageid)
                   "title" to page.title
                    "url" to page.fullurl
                    "thumbnailJson" to Gson().toJson(page.thumbnail)
        }
    }


    fun removeFavouriteById(pageId: Int){
        databaseHelper.use{
            delete(TABLE_NAME, "id ={pageId}", "pageId" to pageId)

        }
    }

    fun isArticleFavourites(pageId: Int): Boolean{
        //get favourites and Filter
        var pages = getAllFavourites()
        return pages.any{ page->
            page.pageid ==pageId
        }
    }

    fun getAllFavourites(): ArrayList<WikiPage>{
        var pages = ArrayList<WikiPage>()

        val articleRowParser = rowParser { id: Int, title: String, url: String, thumnbnailJson: String ->
            val page = WikiPage()
            page.title = title
            page.pageid = id
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumnbnailJson, WikiThumbnail::class.java)

            pages.add(page)
        }

        return pages
    }
}