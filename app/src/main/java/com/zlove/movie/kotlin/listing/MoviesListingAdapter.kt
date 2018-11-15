package com.zlove.movie.kotlin.listing

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.zlove.movie.kotlin.R
import com.zlove.movie.kotlin.api.Api
import com.zlove.movie.kotlin.model.Movie
import kotlinx.android.synthetic.main.movie_grid_item.view.*

class MoviesListingAdapter(private val movies: List<Movie>, val view: MoviesListingView): RecyclerView.Adapter<MoviesListingAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        this.context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_grid_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (movies.isEmpty())
            return 0
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context!!, movies[position], view)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(context: Context, movie: Movie, view: MoviesListingView) {
            itemView.movie_name.text = movie.title

            val options = RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .priority(Priority.HIGH)

            Glide.with(context)
                    .asBitmap()
                    .load(Api.getPosterPath(movie.posterPath))
                    .apply(options)
                    .into(object : BitmapImageViewTarget(itemView.movie_poster) {
                        override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                            super.onResourceReady(bitmap, transition)
                            Palette.from(bitmap).generate { palette -> setBackgroundColor(context, palette!!) }
                        }
                    })

            itemView.setOnClickListener {
                view.onMovieClicked(movie)
            }
        }

        private fun setBackgroundColor(context: Context, palette: Palette) {
            itemView.title_background.setBackgroundColor(palette.getVibrantColor(context.resources.getColor(R.color.black_translucent_60)))
        }
    }
}