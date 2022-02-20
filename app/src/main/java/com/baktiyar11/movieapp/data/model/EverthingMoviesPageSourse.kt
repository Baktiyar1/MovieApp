package com.baktiyar11.movieapp.data.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.baktiyar11.movieapp.data.repository.TopRepository
import retrofit2.HttpException

class EverthingMoviesPageSourse(
    private val moviesServise: TopRepository,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

        val response = moviesServise.getTopRatedMovie()
        return if (response.isSuccessful) {
            val movies = checkNotNull(response.body()).movieList
            val nextKey = if (movies.size < pageSize) null else (page + 1)
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(movies, prevKey, nextKey)
        } else {
            LoadResult.Error(HttpException(response))
        }
    }
}