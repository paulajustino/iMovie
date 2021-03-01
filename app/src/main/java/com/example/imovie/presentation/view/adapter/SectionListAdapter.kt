package com.example.imovie.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imovie.R
import com.example.imovie.SectionUiModel

class SectionListAdapter :
    ListAdapter<SectionUiModel, SectionListAdapter.SectionViewHolder>(SectionsItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.view_holder_list_home, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = getItem(position)
        holder.bind(section)
    }

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView = itemView.findViewById<TextView>(R.id.title_section)
        private val adapterCarousel = CarouselListAdapter()
        private val recyclerViewHorizontal =
            itemView.findViewById<RecyclerView>(R.id.recycler_view_carousel)

        init {
            recyclerViewHorizontal.adapter = adapterCarousel
            recyclerViewHorizontal.isNestedScrollingEnabled = false
        }

        fun bind(section: SectionUiModel) {
            titleTextView.text = section.titleSection
            adapterCarousel.submitList(section.listMovies)
        }
    }
}

object SectionsItemDiffCallback : DiffUtil.ItemCallback<SectionUiModel>() {
    override fun areItemsTheSame(
        oldItem: SectionUiModel,
        newItem: SectionUiModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: SectionUiModel,
        newItem: SectionUiModel
    ): Boolean = oldItem == newItem
}