package com.example.imovie.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.imovie.*
import com.example.imovie.presentation.view.adapter.SectionListAdapter
import com.example.imovie.presentation.view.statusBarHeightOverCard
import com.example.imovie.presentation.viewmodel.HomeResult
import com.example.imovie.presentation.viewmodel.HomeViewModel
import com.example.imovie.utils.addMarginTop
import com.example.imovie.utils.load

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val adapterSection by lazy {
        SectionListAdapter()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false).apply {
            val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
            toolbar.addMarginTop(statusBarHeightOverCard())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetch()

        setClickListeners(view)
        initializeAdapter(view)
        addObservers()
    }

    private fun initializeAdapter(view: View) {
        val recyclerViewVertical = view.findViewById<RecyclerView>(R.id.recycler_view_home)
        recyclerViewVertical.adapter = adapterSection
    }

    private fun setClickListeners(view: View) {
        val addFavoriteMoviesButton = view.findViewById<Button>(R.id.button_add_favorite)
        val movieDetailsButton = view.findViewById<Button>(R.id.button_movie_details)

        addFavoriteMoviesButton.setOnClickListener { viewModel.addFavorite() }
        movieDetailsButton.setOnClickListener { viewModel.details() }
    }

    private fun addObservers() {
        viewModel.homeResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                HomeResult.Loading -> Log.i("HomeFragment", "loading")
                HomeResult.Error -> Log.i("HomeFragment", "error")
                is HomeResult.Success -> adapterSection.submitList(result.sections)
            }
        })

        viewModel.homeHeaderResult.observe(viewLifecycleOwner, Observer { result ->
            addImageOnHeader(result)
        })
    }

    private fun addImageOnHeader(movie: Movie) {
        val imageView = view?.findViewById<ImageView>(R.id.image_header)
        if (movie.posterPath != null) {
            imageView?.load(movie.posterPath)
        }
    }
}
