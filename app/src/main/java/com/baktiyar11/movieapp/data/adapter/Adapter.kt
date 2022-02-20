package com.baktiyar11.movieapp.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baktiyar11.movieapp.R
import com.baktiyar11.movieapp.databinding.MovieItemBinding
import com.baktiyar11.movieapp.data.model.Movie
import com.baktiyar11.movieapp.data.network.POSTER_BASE_URL
import com.squareup.picasso.Picasso

interface ItemClickListener {
    fun showDetails(id: Int)
}

class Adapter(private val actionListener: ItemClickListener) :
    PagingDataAdapter<Movie, Adapter.MovieViewHolder>(MovieDiffItemCallback) {

    var movieList: List<Movie> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        val binding = MovieItemBinding.bind(inflater)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(private var binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {

            val rating = movie.rating * 10
            binding.apply {
                cvMovieTitle.text = movie.title
                cvMovieReleaseDate.text = movie.releaseDate
                val moviePosterURL = POSTER_BASE_URL + movie.posterPath
                Picasso.get()
                    .load(moviePosterURL)
                    .resize(200, 200)
                    .into(cvIvMoviePoster)
                progressView.setProgress(rating.toInt())
                when {
                    rating >= 70 -> {
                        progressView.setProgressColorRes(R.color.green)
                    }
                    rating.toInt() in 41..69 -> {
                        progressView.setProgressColorRes(R.color.orange)
                    }
                    else -> {
                        progressView.setProgressColorRes(R.color.red)
                    }
                }
            }
            itemView.setOnClickListener {
                actionListener.showDetails(movie.id)
            }
        }
    }

    private object MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title && oldItem.id == newItem.id
        }
    }
}