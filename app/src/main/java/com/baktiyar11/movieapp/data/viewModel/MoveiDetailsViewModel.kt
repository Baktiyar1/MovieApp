package com.baktiyar11.movieapp.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baktiyar11.movieapp.data.model.MovieDetails
import com.baktiyar11.movieapp.data.repository.RepositoryDetails
import kotlinx.coroutines.launch
import retrofit2.Response

class MoveiDetailsViewModel : ViewModel() {

    var movieOfListDetails: MutableLiveData<Response<MovieDetails>> = MutableLiveData()
    private val repositoryDetails = RepositoryDetails()

    fun getMovieDetails(id : Int){
        viewModelScope.launch {
            val response: Response<MovieDetails> = repositoryDetails.getMovieDetails(id = id)
            movieOfListDetails.value = response
        }
    }

}