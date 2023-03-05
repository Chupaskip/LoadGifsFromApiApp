package com.example.loadgifsfromapiapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.loadgifsfromapiapp.R
import com.example.loadgifsfromapiapp.databinding.FragmentGifDetailsBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class GifDetailsFragment : BaseFragment<FragmentGifDetailsBinding>() {
    override val viewBinding: FragmentGifDetailsBinding
        get() = FragmentGifDetailsBinding.inflate(layoutInflater)

    private val args: GifDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindDetailsOfGif()
    }

    private fun bindDetailsOfGif() {
        val drawable = CircularProgressDrawable(requireContext())
        val data = args.data
        drawable.setColorSchemeColors(R.color.purple_200,
            R.color.purple_500,
            R.color.purple_700)
        drawable.centerRadius = 30f
        drawable.strokeWidth = 5f
        drawable.start()
        Glide.with(this)
            .load(data.images.original.url)
            .placeholder(drawable)
            .error(R.drawable.img_error)
            .into(binding.ivDetailGif)
        binding.apply {
            collapsingToolbar.title = data.title
            tvImportDate.text =data.importDatetime
            tvRating.text = data.rating
            tvSource.text = data.source
            tvDescription.text = data.user?.description
        }

    }
}