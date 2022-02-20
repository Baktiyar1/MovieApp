package com.baktiyar11.movieapp.data.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar11.movieapp.data.viewModel.MoveiDetailsViewModel
import com.baktiyar11.movieapp.data.model.MovieDetails
import com.baktiyar11.movieapp.data.network.POSTER_BASE_URL
import com.baktiyar11.movieapp.databinding.MoveiDetailsFragmentBinding
import com.bumptech.glide.Glide

class MovieDetailsFragment : Fragment() {

    private val binding: MoveiDetailsFragmentBinding by lazy {
        MoveiDetailsFragmentBinding.inflate(layoutInflater)
    }
    companion object {
        const val  MOVIE_ID_KEY = "key"
    }

    private var viewModelDetails = MoveiDetailsViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val bundle = arguments
        if (bundle != null) {
            val id = arguments?.getSerializable(MOVIE_ID_KEY) as Int
            viewModelDetails.getMovieDetails(id = id)
            viewModelDetails.movieOfListDetails.observe(viewLifecycleOwner){
                bindUI(it = it.body()!!)
            }
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun bindUI(it : MovieDetails){
        binding.apply {
            movieID.text = it.title
            subTitleID.text = it.tagline
            movieInfoID.text = it.status
            overviewPID.text = it.overview
            rateID.rating = it.rating
            releaseDateID.text = it.releaseDate
            runtimeTimeID.text = it.runtime.toString() + " min"
            bugjetMoneyID.text = it.budget.toString() + " рубль"
            revenueMoneyID.text = it.revenue.toString() + " рубль"

        }
        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(binding.movieIVID)
    }

}