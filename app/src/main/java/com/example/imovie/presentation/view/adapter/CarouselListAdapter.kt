package com.example.imovie.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imovie.HomeAdapterListener
import com.example.imovie.MovieUiModel
import com.example.imovie.databinding.ViewHolderCarouselHomeBinding
import com.example.imovie.utils.load
import kotlinx.android.synthetic.main.view_holder_carousel_home.view.*

class CarouselListAdapter constructor(
    private val listener: HomeAdapterListener
) : ListAdapter<MovieUiModel, CarouselListAdapter.CarouselViewHolder>(CarouselsItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CarouselViewHolder(
            ViewHolderCarouselHomeBinding.inflate(layoutInflater, parent, false).root
        )
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, listener)
    }

    class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: MovieUiModel, listener: HomeAdapterListener) {
            if (movie.posterPath != null) {
                itemView.image_movie.load(movie.posterPath)
            } else {
                itemView.image_movie.setImageDrawable(null)
            }

            itemView.setOnClickListener {
                listener.onHomeMovieClicked(movie.id)
            }
        }
    }
}

object CarouselsItemDiffCallback : DiffUtil.ItemCallback<MovieUiModel>() {
    override fun areItemsTheSame(
        oldItem: MovieUiModel,
        newItem: MovieUiModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MovieUiModel,
        newItem: MovieUiModel
    ): Boolean = oldItem == newItem
}