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

    private lateinit var view: MovieDetailsView
    private lateinit var trailerSubscription: Disposable
    private lateinit var reviewSubscription: Disposable

    override fun showDetails(movie: Movie) {
        view.showDetails(movie)

    }

    override fun showTrailers(movie: Movie) {
        trailerSubscription = movieDetailsInteractor.getTrailers(movie.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videos -> onGetTrailersSuccess(videos) }, { onGetTrailersFailure() })
    }

    private fun onGetTrailersSuccess(videos: List<Video>) {
        view.showTrailers(videos)
    }

    private fun onGetTrailersFailure() {}

    override fun showReviews(movie: Movie) {
        reviewSubscription = movieDetailsInteractor.getReviews(movie.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({reviews -> onGetReviewsSuccess(reviews)}, { onGetReviewsFailure() })
    }

    private fun onGetReviewsSuccess(reviews: List<Review>) {
        view.showReviews(reviews)
    }

    private fun onGetReviewsFailure() {}

    override fun showFavoriteButton(movie: Movie) {
        if (favoritesInteractor.isFavorite(movie.id))
            view.showFavorited()
        else
            view.showUnFavorited()
    }

    override fun onFavoriteClick(movie: Movie) {
            if (favoritesInteractor.isFavorite(movie.id)) {
                favoritesInteractor.unFavorite(movie.id)
                view.showUnFavorited()
            } else {
                favoritesInteractor.setFavorite(movie)
                view.showFavorited()
            }
    }

    override fun setView(view: MovieDetailsView) {
        this.view = view
    }

    override fun destroy() {
        ifNotNull(trailerSubscription, reviewSubscription) {
            trailerSubscription, reviewSubscription ->
                RxUtils.unSubscribe(trailerSubscription, reviewSubscription)
        }
    }

    private fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
        if (value1 != null && value2 != null) {
            bothNotNull(value1, value2)
        }
    }

}