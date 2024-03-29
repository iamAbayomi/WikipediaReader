package com.appdot.io.wikipediareader.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.appdot.io.wikipediareader.R
import com.appdot.io.wikipediareader.fragments.ExploreFragment
import com.appdot.io.wikipediareader.fragments.FavouritesFragment
import com.appdot.io.wikipediareader.fragments.HistoryFragment
import kotlinx.android.synthetic.main.activity_main.*

    //MainActivity to hold Explorefragment
    //FavouritesFragment, HistoryFragment
class MainActivity : AppCompatActivity() {

    private val exploreFragment: ExploreFragment
    private val favouritesFragment: FavouritesFragment
    private val historyFragment: HistoryFragment


    //constructor to initialize Fragments
    init {
        exploreFragment = ExploreFragment()
        favouritesFragment = FavouritesFragment()
        historyFragment = HistoryFragment()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)


        when(item.itemId){
            R.id.navigation_explore -> transaction.replace(R.id.fragment_container, exploreFragment)
            R.id.navigation_favourites -> transaction.replace(R.id.fragment_container, favouritesFragment)
            R.id.navigation_history -> transaction.replace(R.id.fragment_container, historyFragment)
        }

        transaction.commit()

        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = findViewById(R.id.navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, exploreFragment)
        transaction.commit()
    }
}
