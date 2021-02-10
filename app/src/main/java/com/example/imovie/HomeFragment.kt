package com.example.imovie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class HomeFragment: Fragment() {

    private lateinit var viewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addFavoriteMoviesButton = view.findViewById<Button>(R.id.button_add_favorite)
        val movieDetailsButton = view.findViewById<Button>(R.id.button_movie_details)

        addFavoriteMoviesButton.setOnClickListener { viewModel.addFavorite() }
        movieDetailsButton.setOnClickListener { viewModel.details() }

        val adapterSection = SectionListAdapter()

        val recyclerViewVertical = view.findViewById<RecyclerView>(R.id.recycler_view_home)
        recyclerViewVertical.adapter = adapterSection

        viewModel.fetch()

        viewModel.homeResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                HomeResult.Loading -> Log.i("HomeFragment", "loading")
                HomeResult.Error -> Log.i("HomeFragment", "error")
                is HomeResult.Success -> adapterSection.submitList(result.sections)
            }
        })
    }
}