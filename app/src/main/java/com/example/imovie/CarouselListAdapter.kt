package com.example.imovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarouselListAdapter : ListAdapter<Movie, CarouselListAdapter.CarouselViewHolder> (CarouselsItemDiffCallback) {

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
            Glide.with(movieImageView)
                    .load(movie.imageUrl)
                    .into(movieImageView);
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