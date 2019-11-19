package com.appdot.io.wikipediareader.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class ArticleDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "ArticleDatabase.db", null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        //define the tales in the database
        db?.createTable("Favourites", true,
                    "id" to INTEGER + PRIMARY_KEY,
                        "title" to TEXT,
                        "url" to TEXT,
                        "thumbnailJson" to TEXT)
        db?.createTable("History", true,
            "id" to INTEGER + PRIMARY_KEY,
                       "title" to TEXT,
                        "url" to TEXT,
                        "thumbnailJSON" to TEXT)



    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}