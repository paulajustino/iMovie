package com.example.imovie.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.gridlayout.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.imovie.MyApplication
import com.example.imovie.databinding.FragmentDetailsBinding
import com.example.imovie.presentation.DetailsViewAction
import com.example.imovie.presentation.model.MovieDetailsUiModel
import com.example.imovie.presentation.model.MovieUiModel
import com.example.imovie.presentation.viewmodel.DetailsResult
import com.example.imovie.presentation.viewmodel.DetailsViewModel
import com.example.imovie.presentation.viewmodel.SimilarResult
import com.example.imovie.utils.load
import com.example.imovie.utils.setMargin
import javax.inject.Inject
import kotlin.math.abs

class DetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailsViewModel> { viewModelProviderFactory }

    private lateinit var bindingDetailsFragment: FragmentDetailsBinding

    private val args: DetailsFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as? MyApplication)?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingDetailsFragment = FragmentDetailsBinding.inflate(inflater, container, false)
        return bindingDetailsFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dispatchViewAction(DetailsViewAction.OnDetailsInitialized(args.id))

        addObservers()
    }

    private fun addObservers() {
        viewModel.detailsResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is DetailsResult.Success -> setSuccessStateOfDetails(result.movie)
            }
        })

        viewModel.similarResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is SimilarResult.Success -> setSuccessStateOfSimilar(result.movie)
            }

        })
    }

    private fun setSuccessStateOfDetails(movie: MovieDetailsUiModel) {
        movie.backdropPath?.let { this.bindingDetailsFragment.imageMovie.load(it) }
        this.bindingDetailsFragment.run {
            titleMovie.text = movie.title
            overviewMovie.text = movie.overview
            runtimeMovie.text = movie.runtime
            releaseDateMovie.text = movie.release
        }
    }

    private fun setSuccessStateOfSimilar(movies: List<MovieUiModel>) {
        with(bindingDetailsFragment.similarMovies.root) {
            removeAllViews()
            movies.take(6).forEachIndexed { index, movie ->
                val imageView = createImageItem(index, columnCount)
                movie.posterPath?.let { imageView.load(it) }
                addView(imageView)
            }
        }
    }

    private fun createImageItem(index: Int, columnCount: Int) : ImageView {
        val imageView = ImageView(context)
        val row = abs(index/columnCount)
        val column = index%columnCount
        val rowSpec = GridLayout.spec(row, GridLayout.FILL, 1F)
        val columnSpec = GridLayout.spec(column, GridLayout.FILL, 1F)
        val params = GridLayout.LayoutParams(rowSpec, columnSpec)
        params.width = GridLayout.LayoutParams.WRAP_CONTENT
        imageView.layoutParams = params
        imageView.adjustViewBounds = true
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.setMargin(8,8,8,8)
        return imageView
    }
}