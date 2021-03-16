package com.example.imovie.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imovie.MovieUiModel
import com.example.imovie.MyApplication
import com.example.imovie.databinding.FragmentHomeBinding
import com.example.imovie.presentation.view.adapter.SectionListAdapter
import com.example.imovie.presentation.view.statusBarHeightOverCard
import com.example.imovie.presentation.viewmodel.HomeResult
import com.example.imovie.presentation.viewmodel.HomeViewModel
import com.example.imovie.utils.addMarginTop
import com.example.imovie.utils.load
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelProviderFactory }

    private lateinit var bindingHomeFragment: FragmentHomeBinding

    private val adapterSection by lazy {
        SectionListAdapter()
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

        viewModel.fetch()

        setClickListeners()
        initializeAdapter()
        addObservers()
    }

    private fun initializeAdapter() {
        bindingHomeFragment.recyclerViewHome.adapter = adapterSection
    }

    private fun setClickListeners() {
        bindingHomeFragment.headerHome.buttonAddFavorite.setOnClickListener {
            viewModel.addFavorite()
        }

        bindingHomeFragment.headerHome.buttonMovieDetails.setOnClickListener {
            viewModel.details()
        }
    }

    private fun addObservers() {
        viewModel.homeResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is HomeResult.Success -> {
                    adapterSection.submitList(result.sections)
                    setSuccessState()
                }
                is HomeResult.Loading -> setLoadingState()
                is HomeResult.Error -> setErrorState()
            }
        })

        viewModel.homeHeaderResult.observe(viewLifecycleOwner, Observer { result ->
            addImageOnHeader(result)
        })
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
