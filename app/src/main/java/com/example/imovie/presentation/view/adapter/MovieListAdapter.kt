package com.example.imovie.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imovie.MovieUiModel
import com.example.imovie.databinding.ViewHolderDetailsMovieBinding
import com.example.imovie.utils.load
import kotlinx.android.synthetic.main.view_holder_details_movie.view.*

class MovieListAdapter :
    ListAdapter<MovieUiModel, MovieListAdapter.MovieViewHolder>(MoviesItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            ViewHolderDetailsMovieBinding.inflate(layoutInflater, parent, false).root
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: MovieUiModel) {
            if (movie.posterPath != null) {
                itemView.image_details.load(movie.posterPath)
            } else {
                itemView.image_details.setImageDrawable(null)
            }
        }
    }
}

object MoviesItemDiffCallback : DiffUtil.ItemCallback<MovieUiModel>() {
    override fun areItemsTheSame(
        oldItem: MovieUiModel,
        newItem: MovieUiModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MovieUiModel,
        newItem: MovieUiModel
    ): Boolean = oldItem == newItem
}