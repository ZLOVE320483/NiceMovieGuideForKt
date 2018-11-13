package com.zlove.movie.kotlin.details

import android.support.v4.app.Fragment
import android.view.View
import com.zlove.movie.kotlin.model.Movie
import com.zlove.movie.kotlin.model.Review
import com.zlove.movie.kotlin.model.Video

class MovieDetailsFragment: Fragment(), MovieDetailsView, View.OnClickListener {

    override fun showDetails(movie: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTrailers(trailers: List<Video>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showReviews(reviews: List<Review>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showFavorited() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUnFavorited() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}