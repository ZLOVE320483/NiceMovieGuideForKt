package com.zlove.movie.kotlin.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zlove.movie.kotlin.R
import com.zlove.movie.kotlin.api.Api
import com.zlove.movie.kotlin.app.BaseApplication
import com.zlove.movie.kotlin.constant.Constants
import com.zlove.movie.kotlin.model.Movie
import com.zlove.movie.kotlin.model.Review
import com.zlove.movie.kotlin.model.Video
import kotlinx.android.synthetic.main.fragment_movie_details.view.*
import kotlinx.android.synthetic.main.review.view.*
import kotlinx.android.synthetic.main.trailers_and_reviews.view.*
import kotlinx.android.synthetic.main.video.view.*
import javax.inject.Inject

class MovieDetailsFragment: Fragment(), MovieDetailsView {

    @Inject lateinit var movieDetailsPresenter: MovieDetailsPresenter
    private lateinit var rootView: View
    private var movie: Movie? = null

    companion object {
        fun getInstance(movie: Movie): MovieDetailsFragment {
            val args = Bundle()
            args.putParcelable(Constants.MOVIE, movie)
            val movieDetailsFragment = MovieDetailsFragment()
            movieDetailsFragment.arguments = args
            return movieDetailsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        (activity!!.application as BaseApplication).createDetailsComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_movie_details, container, false)
        setToolbar()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val movie = arguments!!.get(Constants.MOVIE) as Movie
            this.movie = movie
            movieDetailsPresenter.setView(this)
            movieDetailsPresenter.showDetails(movie)
            movieDetailsPresenter.showFavoriteButton(movie)
        }
    }

    private fun setToolbar() {
        rootView.collapsing_toolbar.setContentScrimColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        rootView.collapsing_toolbar.title = getString(R.string.movie_details)
        rootView.collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar)
        rootView.collapsing_toolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar)
        rootView.collapsing_toolbar.setTitleEnabled(true)

        if (rootView.toolbar != null) {
            (activity as AppCompatActivity).setSupportActionBar(rootView.toolbar)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            // Don't inflate. Tablet is in landscape mode.
        }
    }

    override fun showDetails(movie: Movie) {
        Glide.with(context!!).load(Api.getBackdropPath(movie.backdropPath)).into(rootView.movie_poster)
        rootView.movie_name.text = movie.title
        rootView.movie_year.text = String.format(getString(R.string.release_date), movie.releaseDate)
        rootView.movie_rating.text = String.format(getString(R.string.rating), movie.voteAverage.toString())
        rootView.movie_description.text = movie.overview
        movieDetailsPresenter.showTrailers(movie)
        movieDetailsPresenter.showReviews(movie)
        rootView.favorite.setOnClickListener{ onFavoriteClick() }
    }

    override fun showTrailers(trailers: List<Video>) {
        if (trailers.isEmpty()) {
            rootView.trailers_label.visibility = View.GONE
            rootView.trailers.visibility = View.GONE
            rootView.trailers_container.visibility = View.GONE

        } else {
            rootView.trailers_label.visibility = View.VISIBLE
            rootView.trailers.visibility = View.VISIBLE
            rootView.trailers_container.visibility = View.VISIBLE

            rootView.trailers.removeAllViews()
            val inflater = activity!!.layoutInflater
            val options = RequestOptions()
                    .placeholder(R.color.colorPrimary)
                    .centerCrop()
                    .override(150, 150)

            for (trailer in trailers) {
                val thumbContainer = inflater.inflate(R.layout.video, rootView.trailers, false)
                thumbContainer.video_thumb.setTag(R.id.glide_tag, Video.getUrl(trailer))
                thumbContainer.video_thumb.requestLayout()
                thumbContainer.video_thumb.setOnClickListener{ onThumbnailClick(thumbContainer.video_thumb) }
                Glide.with(context!!)
                        .load(Video.getThumbnailUrl(trailer))
                        .apply(options)
                        .into(thumbContainer.video_thumb)
                rootView.trailers.addView(thumbContainer)
            }
        }
    }

    override fun showReviews(reviews: List<Review>) {
        if (reviews.isEmpty()) {
            rootView.reviews_label.visibility = View.GONE
            rootView.reviews.visibility = View.GONE
        } else {
            rootView.reviews_label.visibility = View.VISIBLE
            rootView.reviews.visibility = View.VISIBLE

            rootView.reviews.removeAllViews()
            val inflater = activity!!.layoutInflater
            for (review in reviews) {
                val reviewContainer = inflater.inflate(R.layout.review, rootView.reviews, false) as ViewGroup
                reviewContainer.review_author.text = review.author
                reviewContainer.review_content.text = review.content
                reviewContainer.review_content.setOnClickListener{ onReviewClick(reviewContainer.review_content as TextView) }
                rootView.reviews.addView(reviewContainer)
            }
        }
    }

    override fun showFavorited() {
        rootView.favorite.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_white_24dp))
    }

    override fun showUnFavorited() {
        rootView.favorite.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_border_white_24dp))
    }

    private fun onReviewClick(view: TextView) {
        if (view.maxLines == 5) {
            view.maxLines = 500
        } else {
            view.maxLines = 5
        }
    }

    private fun onThumbnailClick(view: View) {
        val videoUrl = view.getTag(R.id.glide_tag) as String
        val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        startActivity(playVideoIntent)
    }

    private fun onFavoriteClick() {
        movie?.let {
            movieDetailsPresenter.onFavoriteClick(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieDetailsPresenter.destroy()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity!!.application as BaseApplication).releaseDetailsComponent()
    }
}