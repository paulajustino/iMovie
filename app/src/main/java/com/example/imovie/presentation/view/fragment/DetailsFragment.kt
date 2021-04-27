package com.example.imovie.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.imovie.MyApplication
import com.example.imovie.databinding.FragmentDetailsBinding
import com.example.imovie.presentation.DetailsViewAction
import com.example.imovie.presentation.model.MovieDetailsUiModel
import com.example.imovie.presentation.viewmodel.DetailsResult
import com.example.imovie.presentation.viewmodel.DetailsViewModel
import com.example.imovie.utils.load
import javax.inject.Inject

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
                is DetailsResult.Success -> setSuccessState(result.movie)
            }
        })
    }

    private fun setSuccessState(movie: MovieDetailsUiModel) {
        movie.backdropPath?.let { this.bindingDetailsFragment.imageMovie.load(it) }
        this.bindingDetailsFragment.run {
            titleMovie.text = movie.title
            overviewMovie.text = movie.overview
            runtimeMovie.text = movie.runtime
            releaseDateMovie.text = movie.release
        }
    }
}