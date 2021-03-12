package com.example.imovie.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imovie.MovieUiModel
import com.example.imovie.databinding.ViewHolderCarouselHomeBinding
import com.example.imovie.utils.load
import kotlinx.android.synthetic.main.view_holder_carousel_home.view.*

class CarouselListAdapter :
    ListAdapter<MovieUiModel, CarouselListAdapter.CarouselViewHolder>(CarouselsItemDiffCallback) {

    private lateinit var bindingCarousel: ViewHolderCarouselHomeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        bindingCarousel = ViewHolderCarouselHomeBinding.inflate(layoutInflater, parent, false)
        return CarouselViewHolder(bindingCarousel.root)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImageView = itemView.image_movie

        fun bind(movie: MovieUiModel) {
            if (movie.posterPath != null) {
                movieImageView.load(movie.posterPath)
            } else {
                movieImageView.setImageDrawable(null)
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