package com.example.loadgifsfromapiapp.ui.adapters

import android.content.Context
import android.graphics.CornerPathEffect
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.loadgifsfromapiapp.R
import com.example.loadgifsfromapiapp.databinding.ItemGifBinding
import com.example.loadgifsfromapiapp.models.Data


class GifAdapter : PagingDataAdapter<Data, GifAdapter.GifViewHolder>(differCallback) {

    inner class GifViewHolder(val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context

    lateinit var onItemClick: ((Data) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        context = parent.context
        return GifViewHolder(ItemGifBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = getItem(position)!!
        holder.binding.apply {
            val drawable = CircularProgressDrawable(context)
            drawable.setColorSchemeColors(R.color.purple_200,
                R.color.purple_500,
                R.color.purple_700)
            drawable.centerRadius = 30f
            drawable.strokeWidth = 5f
            drawable.start()
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(20))
            Glide.with(holder.itemView.context)
                .load(gif.images.original.url)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(200)
                .placeholder(drawable)
                .error(R.drawable.img_error)
                .into(ivGif)
        }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(gif)
        }
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }
}
