package com.baktiyar11.movieapp.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.baktiyar11.movieapp.data.model.EverthingMoviesPageSourse
import com.baktiyar11.movieapp.data.model.Movie
import com.baktiyar11.movieapp.data.model.MovieResponse
import com.baktiyar11.movieapp.data.repository.TopRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Response

class TopViewModel : ViewModel() {

    var movieOfListTop: MutableLiveData<Response<MovieResponse>> = MutableLiveData()
    private val repositoryTop = TopRepository()

    private fun create(): PagingSource<Int, Movie> {
        return EverthingMoviesPageSourse(repositoryTop)
    }

    val paging: StateFlow<PagingData<Movie>> =
        Pager(PagingConfig(pageSize = 5)) {
            create()
        }.flow
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun getTopRatedMovie() {
        viewModelScope.launch {
            val response: Response<MovieResponse> = repositoryTop.getTopRatedMovie()
            movieOfListTop.value = response
        }
    }

    fun getPopularMovie(){
        viewModelScope.launch {
            val response: Response<MovieResponse> = repositoryTop.getPopularMovie()
            movieOfListTop.value = response
        }
    }

    fun getUpcomingMovie(){
        viewModelScope.launch {
            val response: Response<MovieResponse> = repositoryTop.getUpcomingMovie()
            movieOfListTop.value = response
        }
    }

    fun getNowPlayingMovie(){
        viewModelScope.launch {
            val response: Response<MovieResponse> = repositoryTop.getNowPlayingMovie()
            movieOfListTop.value = response
        }
    }
}