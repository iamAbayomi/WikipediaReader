package com.appdot.io.wikipediareader

import android.app.Application
import com.appdot.io.wikipediareader.managers.WikiManager
import com.appdot.io.wikipediareader.provider.ArticleDataProvider
import com.appdot.io.wikipediareader.repositories.ArticleDatabaseOpenHelper
import com.appdot.io.wikipediareader.repositories.FavouritesRepository
import com.appdot.io.wikipediareader.repositories.HistoryRepository

class WikiApplication: Application() {
    private var dbHelper : ArticleDatabaseOpenHelper? = null
    private var favouritesRepository: FavouritesRepository? = null
    private var historyRepository: HistoryRepository? = null
    private var wikiProvider: ArticleDataProvider?= null

    var wikiManager : WikiManager? = null
        private set

    override fun onCreate(){
        super.onCreate()

        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favouritesRepository = FavouritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!, favouritesRepository!! ,historyRepository!! )
    }
}