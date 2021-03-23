package com.example.imovie.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imovie.MyApplication
import com.example.imovie.databinding.FragmentDetailsBinding
import com.example.imovie.presentation.view.adapter.MovieListAdapter

class DetailsFragment : Fragment() {

    private lateinit var bindingDetailsFragment: FragmentDetailsBinding

    private val adapterDetails by lazy {
        MovieListAdapter()
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
        bindingDetailsFragment = FragmentDetailsBinding.inflate(inflater, container, false)
        return bindingDetailsFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeAdapter()
    }

    private fun initializeAdapter() {
        bindingDetailsFragment.recyclerViewDetails.layoutManager = GridLayoutManager(context, 2)
        bindingDetailsFragment.recyclerViewDetails.adapter = adapterDetails
    }
}