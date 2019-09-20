package com.appdot.io.wikipediademo.provider

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.appdot.io.wikipediademo.model.Urls
import com.appdot.io.wikipediademo.model.WikiResult

import com.google.gson.Gson

import java.io.Reader

class ArticleDataProvider {

    init{
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Pluralsight Wikipedia")
    }

    fun search(term: String, skip: Int, take: Int, responseHandler: (result: WikiResult) -> Unit?){
        Urls.getSearchUrl(term, skip, take).httpGet()
            .responseObject(WikipediaDataDeseriaLizer()){ _, response, result ->

                if(response.httpStatusCode != 200){
                    throw Exception("Unable to get articles")
                }
                val(data, _) = result
                responseHandler.invoke(data as WikiResult)

            }
    }

    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?){
        Urls.getRandomUrl(take).httpGet()
            .responseObject(WikipediaDataDeseriaLizer()){ request,response, result ->

                if(response.httpStatusCode != 200){
                    throw Exception("Unable to get articles")
                }

                val(data, _) = result
                responseHandler.invoke(data as WikiResult)
            }
    }

    class WikipediaDataDeseriaLizer : ResponseDeserializable<WikiResult>{
        override fun deserialize(reader: Reader) : WikiResult? = Gson().fromJson(reader, WikiResult::class.java)
    }


}