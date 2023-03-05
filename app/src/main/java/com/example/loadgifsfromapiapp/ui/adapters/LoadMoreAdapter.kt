package com.example.loadgifsfromapiapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.loadgifsfromapiapp.databinding.LoadStateItemBinding

class LoadMoreAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadMoreAdapter.ViewHolder>() {

    private lateinit var binding: LoadStateItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        binding = LoadStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(retry)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry() }
        }

        fun bind(state: LoadState) {
            binding.apply {
                progressBar.isVisible = state is LoadState.Loading
                retryButton.isVisible = state is LoadState.Error
                errorMsg.isVisible = state is LoadState.Error
                if (loadState is LoadState.Error) {
                    errorMsg.text = (loadState as LoadState.Error).error.localizedMessage
                }
            }
        }
    }
}