package com.appdot.io.wikipediademo.repositories

import com.appdot.io.wikipediademo.model.WikiPage
import com.appdot.io.wikipediademo.model.WikiThumbnail
import com.google.gson.Gson
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser

class HistoryRepository(val databaseHelper: ArticleDatabaseOpenHelper){
    private val TABLE_NAME : String = "History"

    fun addFavourites(page: WikiPage){
        databaseHelper.use{
            insert(TABLE_NAME,
                "id"  to page.pageid)
            "title" to page.title
            "url" to page.fullurl
            "thumbnailJson" to Gson().toJson(page.thumbnail)
        }
    }


    fun removePageById(pageId: Int){
        databaseHelper.use{
            delete(TABLE_NAME, "id ={pageId}", "pageId" to pageId)

        }
    }

    fun getAllHistory(): ArrayList<WikiPage>{
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

