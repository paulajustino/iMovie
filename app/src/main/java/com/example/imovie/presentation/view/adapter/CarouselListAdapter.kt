package com.example.imovie.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imovie.Movie
import com.example.imovie.R
import com.example.imovie.utils.load

class CarouselListAdapter :
    ListAdapter<Movie, CarouselListAdapter.CarouselViewHolder>(CarouselsItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.view_holder_carousel_home, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImageView = itemView.findViewById<ImageView>(R.id.image_movie)

        fun bind(movie: Movie) {
            if (movie.posterPath != null) {
                movieImageView.load(movie.posterPath)
            } else {
                movieImageView.setImageDrawable(null)
            }
        }
    }
}

object CarouselsItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean = oldItem == newItem
}