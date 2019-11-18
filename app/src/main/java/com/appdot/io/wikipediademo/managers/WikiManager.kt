package com.appdot.io.wikipediademo.managers

import com.appdot.io.wikipediademo.model.WikiPage
import com.appdot.io.wikipediademo.model.WikiResult
import com.appdot.io.wikipediademo.provider.ArticleDataProvider
import com.appdot.io.wikipediademo.repositories.FavouritesRepository
import com.appdot.io.wikipediademo.repositories.HistoryRepository

class WikiManager(private val provider: ArticleDataProvider,
                  private val favoritesRepository: FavouritesRepository,
                  private val historyRepository: HistoryRepository) {

    private var favouriteCache: ArrayList<WikiPage>? = null
    private var historyCache: ArrayList<WikiPage>? = null

    fun search(term: String, skip: Int, take: Int, handler: (result: WikiResult) -> Unit?) {
        return provider.search(term, skip, take, handler)
    }

    fun getRandom(take: Int, handler: (result: WikiResult) -> Unit?) {
        return provider.getRandom(take, handler)
    }

    fun getHistory(): ArrayList<WikiPage>? {
        if (historyCache == null)
            historyCache = historyRepository.getAllHistory()
        return historyCache
    }

    fun getFavourites(): ArrayList<WikiPage>? {
        if (favouriteCache == null)
            favouriteCache = favoritesRepository.getAllFavourites()

        return favouriteCache
    }

    fun addFavourite(page: WikiPage) {
        favouriteCache?.add(page)
        favoritesRepository.addFavourites(page)
    }

    fun removeFavourite(pageId: Int) {
        favoritesRepository.removeFavouriteById(pageId)
        favouriteCache = favouriteCache!!.filter { it.pageid != pageId } as ArrayList<WikiPage>
    }

    fun getIsFavourites(pageId: Int): Boolean {
        return favoritesRepository.isArticleFavourites(pageId)
    }


    fun addHistory(page: WikiPage) {
        historyCache?.add(page)
        historyRepository.addFavourites(page)
    }

    fun clearHistory() {
        historyCache?.clear()
        val allHistory = historyRepository.getAllHistory()
        allHistory.forEach {
            historyRepository.removePageById(it.pageid!!)
        }


    }
}
