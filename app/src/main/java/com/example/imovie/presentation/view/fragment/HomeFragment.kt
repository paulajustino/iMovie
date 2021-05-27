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
import androidx.navigation.fragment.findNavController
import com.example.imovie.*
import com.example.imovie.databinding.FragmentHomeBinding
import com.example.imovie.presentation.HomeViewAction
import com.example.imovie.presentation.HomeViewState
import com.example.imovie.presentation.model.MovieUiModel
import com.example.imovie.presentation.view.adapter.SectionListAdapter
import com.example.imovie.presentation.view.listener.HomeAdapterListener
import com.example.imovie.presentation.view.statusBarHeightOverCard
import com.example.imovie.presentation.viewmodel.HomeResult
import com.example.imovie.presentation.viewmodel.HomeViewModel
import com.example.imovie.utils.addMarginTop
import com.example.imovie.utils.load
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject

class HomeFragment : Fragment(), HomeAdapterListener {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelProviderFactory }

    private lateinit var bindingHomeFragment: FragmentHomeBinding

    private val adapterSection by lazy {
        SectionListAdapter(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as? MyApplication)?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingHomeFragment = FragmentHomeBinding.inflate(inflater, container, false)
        return bindingHomeFragment.root.apply {
            this.toolbar.addMarginTop(statusBarHeightOverCard())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

        setClickListeners()
        initializeAdapter()
        addObservers()
    }

    private fun initializeAdapter() {
        bindingHomeFragment.recyclerViewHome.adapter = adapterSection
    }

    private fun setClickListeners() {
        bindingHomeFragment.headerHome.buttonAddFavorite.setOnClickListener {
            viewModel.dispatchViewAction(HomeViewAction.OnFavoriteMoviesClicked)
        }

        bindingHomeFragment.headerHome.buttonMovieDetails.setOnClickListener {
            viewModel.dispatchViewAction(HomeViewAction.OnHomeMovieClicked)
        }

        bindingHomeFragment.headerHome.imageHeader.setOnClickListener {
            viewModel.dispatchViewAction(HomeViewAction.OnHomeMovieClicked)
        }
    }

    private fun addObservers() {
        viewModel.viewState.homeResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is HomeResult.Success -> {
                    adapterSection.submitList(result.sections)
                    setSuccessState()
                }
                is HomeResult.Loading -> setLoadingState()
                is HomeResult.Error -> setErrorState()
            }
        })

        viewModel.viewState.homeHeaderResult.observe(viewLifecycleOwner, Observer { result ->
            addImageOnHeader(result)
        })

        viewModel.viewState.action.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is HomeViewState.Action.OpenDetails -> {
                    val homeAction =
                        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(action.id)
                    findNavController().navigate(homeAction)
                }
            }
        })
    }

    override fun onHomeMovieClicked(id: String) {
        viewModel.dispatchViewAction(HomeViewAction.OnCarouselHomeMovieClicked(id))
    }

    private fun addImageOnHeader(movie: MovieUiModel) {
        if (movie.posterPath != null) {
            bindingHomeFragment.headerHome.imageHeader.load(movie.posterPath)
        }
    }

    private fun setLoadingState() {
        bindingHomeFragment.loading.root.visibility = View.VISIBLE
        bindingHomeFragment.error.root.visibility = View.INVISIBLE
        bindingHomeFragment.appBar.visibility = View.INVISIBLE
        bindingHomeFragment.recyclerViewHome.visibility = View.INVISIBLE
    }

    private fun setSuccessState() {
        bindingHomeFragment.loading.root.visibility = View.INVISIBLE
        bindingHomeFragment.error.root.visibility = View.INVISIBLE
        bindingHomeFragment.appBar.visibility = View.VISIBLE
        bindingHomeFragment.recyclerViewHome.visibility = View.VISIBLE
    }

    private fun setErrorState() {
        bindingHomeFragment.loading.root.visibility = View.INVISIBLE
        bindingHomeFragment.error.root.visibility = View.VISIBLE
        bindingHomeFragment.appBar.visibility = View.INVISIBLE
        bindingHomeFragment.recyclerViewHome.visibility = View.INVISIBLE
    }
}
