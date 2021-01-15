package com.example.imovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SectionListAdapter : ListAdapter<Section, SectionListAdapter.SectionViewHolder>(SectionsItemDiffCallback) {

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
        private val recyclerViewHorizontal = itemView.findViewById<RecyclerView>(R.id.recycler_view_carousel)

        init {
            recyclerViewHorizontal.adapter = adapterCarousel
        }

        fun bind(section: Section) {
            titleTextView.text = section.titleSection
            adapterCarousel.submitList(section.listMovies)
        }
    }
}

object SectionsItemDiffCallback : DiffUtil.ItemCallback<Section>() {
    override fun areItemsTheSame(
        oldItem: Section,
        newItem: Section
    ): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(
        oldItem: Section,
        newItem: Section
    ): Boolean = oldItem == newItem
}