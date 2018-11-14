package com.zlove.movie.kotlin.listing.sorting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioGroup
import com.zlove.movie.kotlin.R
import com.zlove.movie.kotlin.app.BaseApplication
import com.zlove.movie.kotlin.listing.MoviesListingPresenter
import kotlinx.android.synthetic.main.sorting_options.view.*
import javax.inject.Inject

class SortingDialogFragment: DialogFragment(), SortingDialogView, RadioGroup.OnCheckedChangeListener {

    @Inject lateinit var sortingDialogPresenter: SortingDialogPresenter
    private var dialogView: View? = null

    companion object {
        var moviesListingPresenter: MoviesListingPresenter? = null

        fun newInstance(moviesListingPresenter: MoviesListingPresenter): SortingDialogFragment {
            this.moviesListingPresenter = moviesListingPresenter
            return SortingDialogFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        (context?.applicationContext as BaseApplication).getListingComponent().inject(this)
        sortingDialogPresenter.setView(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialogView = inflater.inflate(R.layout.sorting_options, null)
        initViews()

        val dialog = Dialog(activity!!)
        dialog.setContentView(dialogView)
        dialog.setTitle(R.string.sort_by)
        dialog.show()
        return dialog
    }

    private fun initViews() {
        sortingDialogPresenter.setLastSavedOption()
        dialogView!!.sorting_group.setOnCheckedChangeListener(this)
    }

    override fun setPopularChecked() {
        dialogView!!.most_popular.isChecked = true
    }

    override fun setNewestChecked() {
        dialogView!!.newest.isChecked = true
    }

    override fun setHighestRatedChecked() {
        dialogView!!.highest_rated.isChecked = true
    }

    override fun setFavoritesChecked() {
        dialogView!!.favorites.isChecked = true
    }

    override fun dismissDialog() {
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        sortingDialogPresenter.destroy()
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when(checkedId) {
            R.id.most_popular -> {
                sortingDialogPresenter.onPopularMoviesSelected()
                moviesListingPresenter!!.firstPage()
            }
            R.id.highest_rated -> {
                sortingDialogPresenter.onHighestRatedMoviesSelected()
                moviesListingPresenter!!.firstPage()
            }
            R.id.favorites -> {
                sortingDialogPresenter.onFavoritesSelected()
                moviesListingPresenter!!.firstPage()
            }
            R.id.newest -> {
                sortingDialogPresenter.onNewestMoviesSelected()
                moviesListingPresenter!!.firstPage()
            }
        }
    }
}