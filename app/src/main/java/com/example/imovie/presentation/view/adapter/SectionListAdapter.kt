package com.example.imovie.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imovie.presentation.view.listener.HomeAdapterListener
import com.example.imovie.presentation.model.SectionUiModel
import com.example.imovie.databinding.ViewHolderListHomeBinding
import kotlinx.android.synthetic.main.view_holder_list_home.view.*

class SectionListAdapter constructor(
    private val listener: HomeAdapterListener
) : ListAdapter<SectionUiModel, SectionListAdapter.SectionViewHolder>(SectionsItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SectionViewHolder(
            ViewHolderListHomeBinding.inflate(layoutInflater, parent, false).root
        )
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = getItem(position)
        holder.bind(section)
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val adapterCarousel = CarouselListAdapter(listener = listener)

        init {
            itemView.recycler_view_carousel.adapter = adapterCarousel
            itemView.recycler_view_carousel.isNestedScrollingEnabled = false
        }

        fun bind(section: SectionUiModel) {
            itemView.title_section.text = section.titleSection
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