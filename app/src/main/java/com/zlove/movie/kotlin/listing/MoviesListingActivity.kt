package com.zlove.movie.kotlin.listing

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.zlove.movie.kotlin.R
import com.zlove.movie.kotlin.constant.Constants
import com.zlove.movie.kotlin.details.MovieDetailsActivity
import com.zlove.movie.kotlin.details.MovieDetailsFragment
import com.zlove.movie.kotlin.model.Movie
import com.zlove.movie.kotlin.utils.RxUtils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.concurrent.TimeUnit

class MoviesListingActivity: AppCompatActivity(), MoviesListingFragment.Callback {

    companion object {
       const val DETAILS_FRAGMENT = "DetailsFragment"
    }

    private var twoPaneMode: Boolean = false
    private lateinit var searchViewTextSubscription: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()

        if (movie_details_container != null) {
            twoPaneMode = true
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.movie_details_container, MovieDetailsFragment())
                        .commit()
            }
        } else {
            twoPaneMode = false
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.movie_guide)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val mlFragment = supportFragmentManager.findFragmentById(R.id.fragment_listing) as MoviesListingFragment?
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                val mlFragment = supportFragmentManager.findFragmentById(R.id.fragment_listing) as MoviesListingFragment?
                mlFragment?.searchViewBackButtonClicked()
                return true
            }
        })

        searchViewTextSubscription = RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe { charSequence ->
                    if (charSequence.isNotEmpty()) {
                        mlFragment?.searchViewClicked(charSequence.toString())
                    }
                }
        return true
    }

    override fun onMoviesLoaded(movie: Movie) {
        if (twoPaneMode) {
            loadMovieFragment(movie)
        } else {
            // Do not load in single pane view
        }
    }

    override fun onMovieClicked(movie: Movie) {
        if (twoPaneMode) {
            loadMovieFragment(movie)
        } else {
            startMovieActivity(movie)
        }
    }

    private fun startMovieActivity(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        val extras = Bundle()
        extras.putParcelable(Constants.MOVIE, movie)
        intent.putExtras(extras)
        startActivity(intent)
    }

    private fun loadMovieFragment(movie: Movie) {
        val movieDetailsFragment = MovieDetailsFragment.getInstance(movie)
        supportFragmentManager.beginTransaction()
                .replace(R.id.movie_details_container, movieDetailsFragment, DETAILS_FRAGMENT)
                .commit()
    }

    override fun onDestroy() {
        RxUtils.unSubscribe(searchViewTextSubscription)
        super.onDestroy()
    }
}