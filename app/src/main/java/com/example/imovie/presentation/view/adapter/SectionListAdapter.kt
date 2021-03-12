package com.example.imovie.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imovie.SectionUiModel
import com.example.imovie.databinding.ViewHolderListHomeBinding
import kotlinx.android.synthetic.main.view_holder_list_home.view.*

class SectionListAdapter :
    ListAdapter<SectionUiModel, SectionListAdapter.SectionViewHolder>(SectionsItemDiffCallback) {

    private lateinit var bindingSection: ViewHolderListHomeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        bindingSection = ViewHolderListHomeBinding.inflate(layoutInflater, parent, false)
        return SectionViewHolder(bindingSection.root)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = getItem(position)
        holder.bind(section)
    }

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView = itemView.title_section
        private val adapterCarousel = CarouselListAdapter()
        private val recyclerViewHorizontal = itemView.recycler_view_carousel

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