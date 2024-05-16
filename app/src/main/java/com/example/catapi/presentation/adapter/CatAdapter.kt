package com.example.catapi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.catapi.domain.model.CatListModel
import com.example.catapi.databinding.FragmentListCatItemBinding
import com.example.catapi.presentation.adapter.imageview.loadImageFromUrl

internal class CatAdapter(
//    private val itemClickListener: (CatListModel) -> Unit
):
    ListAdapter<CatListModel, CatAdapter.CatViewHolder>(CatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentListCatItemBinding.inflate(inflater, parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = getItem(position)
        holder.bind(cat)
    }
    inner class CatViewHolder(private val binding: FragmentListCatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: CatListModel) {
           binding.imageCat.loadImageFromUrl(cat.url)
//
//                setOnClickListener {
//                    itemClickListener(cat)
//                }

        }
    }

    class CatDiffCallback : DiffUtil.ItemCallback<CatListModel>() {
        override fun areItemsTheSame(oldItem: CatListModel, newItem: CatListModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CatListModel, newItem: CatListModel) = oldItem == newItem
    }
}