package com.zlove.movie.kotlin.listing

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.zlove.movie.kotlin.R
import com.zlove.movie.kotlin.app.BaseApplication
import com.zlove.movie.kotlin.constant.Constants
import com.zlove.movie.kotlin.listing.sorting.SortingDialogFragment
import com.zlove.movie.kotlin.model.Movie
import kotlinx.android.synthetic.main.fragment_movies.view.*
import java.util.*
import javax.inject.Inject

class MoviesListingFragment: Fragment(), MoviesListingView {

    @Inject lateinit var moviesListingPresenter: MoviesListingPresenter
    private var callback: Callback? = null
    private var rootView: View? = null

    private var adapter: RecyclerView.Adapter<*>? = null
    private var movies: MutableList<Movie>? = ArrayList(20)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as Callback?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
        (context?.applicationContext as BaseApplication).createListingComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_movies, container, false)
        initLayoutReferences()

        rootView!!.movies_listing.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    moviesListingPresenter.nextPage()
                }
            }
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesListingPresenter.setView(this)
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList(Constants.MOVIE)
            adapter!!.notifyDataSetChanged()
            rootView!!.movies_listing.visibility = View.VISIBLE
        } else {
            moviesListingPresenter.firstPage()
        }
    }

    private fun initLayoutReferences() {
        rootView!!.movies_listing.setHasFixedSize(true)
        val columns: Int
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columns = 2
        } else {
            columns = resources.getInteger(R.integer.no_of_columns)
        }
        val layoutManager = GridLayoutManager(activity, columns)

        rootView!!.movies_listing.layoutManager = layoutManager
        adapter = MoviesListingAdapter(movies!!, this)
        rootView!!.movies_listing.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_sort -> {
                moviesListingPresenter.firstPage()
                displaySortingOptions()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displaySortingOptions() {
        val sortingDialogFragment = SortingDialogFragment.newInstance(moviesListingPresenter)
        sortingDialogFragment.show(fragmentManager!!, "Select Quantity")
    }

    override fun showMovies(movies: List<Movie>) {
        this.movies!!.clear()
        this.movies!!.addAll(movies)
        rootView!!.movies_listing.visibility = View.VISIBLE
        adapter!!.notifyDataSetChanged()
        callback!!.onMoviesLoaded(movies[0])
    }

    override fun loadingStarted() {
        Snackbar.make(rootView!!.movies_listing, R.string.loading_movies, Snackbar.LENGTH_SHORT).show()
    }

    override fun loadingFailed(errorMessage: String) {
        Snackbar.make(rootView!!.movies_listing, errorMessage, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onMovieClicked(movie: Movie) {
        callback!!.onMovieClicked(movie)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        moviesListingPresenter.destroy()
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity!!.application as BaseApplication).releaseListingComponent()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Constants.MOVIE, movies as ArrayList<out Parcelable>)
    }


    fun searchViewClicked(searchText: String) {
        moviesListingPresenter.searchMovie(searchText)
    }

    fun searchViewBackButtonClicked() {
        moviesListingPresenter.searchMovieBackPressed()
    }

    interface Callback {
        fun onMoviesLoaded(movie: Movie)
        fun onMovieClicked(movie: Movie)
    }
}
