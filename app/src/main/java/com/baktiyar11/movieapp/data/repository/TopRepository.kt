package com.baktiyar11.movieapp.data.repository

import com.baktiyar11.movieapp.data.model.MovieResponse
import com.baktiyar11.movieapp.data.network.RetrofitInstance
import retrofit2.Response

class TopRepository {
    suspend fun getTopRatedMovie(): Response<MovieResponse>{
        return RetrofitInstance.api.getTopRatedMovie()
    }

    suspend fun getPopularMovie(): Response<MovieResponse> {
        return RetrofitInstance.api.getPopularMovie()
    }

    suspend fun getUpcomingMovie(): Response<MovieResponse>{
        return RetrofitInstance.api.getUpcomingMovie()
    }

    suspend fun getNowPlayingMovie(): Response<MovieResponse>{
        return RetrofitInstance.api.getNowPlayingMovie()
    }
}