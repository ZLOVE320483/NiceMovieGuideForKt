package com.zlove.movie.kotlin.details

import com.zlove.movie.kotlin.favorites.FavoritesInteractor
import com.zlove.movie.kotlin.model.Movie
import com.zlove.movie.kotlin.model.Review
import com.zlove.movie.kotlin.model.Video
import com.zlove.movie.kotlin.utils.RxUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsPresenterImpl(private val movieDetailsInteractor: MovieDetailsInteractor,
                                private val favoritesInteractor: FavoritesInteractor): MovieDetailsPresenter {

    private var view: MovieDetailsView? = null
    private var trailerSubscription: Disposable? = null
    private var reviewSubscription: Disposable? = null

    private fun isViewAttached(): Boolean {
        return view != null
    }

    override fun showDetails(movie: Movie) {
        if (isViewAttached())
            view!!.showDetails(movie)
    }

    override fun showTrailers(movie: Movie) {
        trailerSubscription = movieDetailsInteractor.getTrailers(movie.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videos -> onGetTrailersSuccess(videos) }, { onGetTrailersFailure() })
    }

    private fun onGetTrailersSuccess(videos: List<Video>) {
        if (isViewAttached())
            view!!.showTrailers(videos)
    }

    private fun onGetTrailersFailure() {}

    override fun showReviews(movie: Movie) {
        reviewSubscription = movieDetailsInteractor.getReviews(movie.id)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({reviews -> onGetReviewsSuccess(reviews)}, { onGetReviewsFailure() })
    }

    private fun onGetReviewsSuccess(reviews: List<Review>) {
        if (isViewAttached())
            view!!.showReviews(reviews)
    }

    private fun onGetReviewsFailure() {}

    override fun showFavoriteButton(movie: Movie) {
        val isFavorite = favoritesInteractor.isFavorite(movie.id)
        if (isViewAttached()) {
            if (isFavorite) {
                view!!.showFavorited()
            } else {
                view!!.showUnFavorited()
            }
        }
    }

    override fun onFavoriteClick(movie: Movie) {
        if (isViewAttached()) {
            val isFavorite = favoritesInteractor.isFavorite(movie.id)
            if (isFavorite) {
                favoritesInteractor.unFavorite(movie.id)
                view!!.showUnFavorited()
            } else {
                favoritesInteractor.setFavorite(movie)
                view!!.showFavorited()
            }
        }
    }

    override fun setView(view: MovieDetailsView) {
        this.view = view
    }

    override fun destroy() {
        this.view = null
        RxUtils.unSubscribe(trailerSubscription!!, reviewSubscription!!)
    }
}