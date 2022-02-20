package com.baktiyar11.movieapp.data.repository

import com.baktiyar11.movieapp.data.model.MovieDetails
import com.baktiyar11.movieapp.data.network.RetrofitInstance
import retrofit2.Response

class RepositoryDetails {
    suspend fun getMovieDetails(id: Int) : Response<MovieDetails>{
        return RetrofitInstance.api.getMovieDetails(id = id)
    }
}