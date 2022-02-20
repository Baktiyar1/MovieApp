package com.baktiyar11.movieapp.data.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.baktiyar11.movieapp.R
import com.baktiyar11.movieapp.data.viewModel.TopViewModel
import com.baktiyar11.movieapp.data.adapter.Adapter
import com.baktiyar11.movieapp.data.adapter.ItemClickListener
import com.baktiyar11.movieapp.data.fragments.MovieDetailsFragment.Companion.MOVIE_ID_KEY
import com.baktiyar11.movieapp.databinding.TopFragmentBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@DelicateCoroutinesApi
class TopFragment : Fragment() {

    private val binding: TopFragmentBinding by lazy {
        TopFragmentBinding.inflate(layoutInflater)
    }
    private var viewModelTop = TopViewModel()

    private var adapterTop = Adapter(object : ItemClickListener {
        override fun showDetails(id: Int) {
            findNavController().navigate(R.id.action_topFragment_to_movieDetailsFragment,
                bundleOf(MOVIE_ID_KEY to id))
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupSimpleSpinner()
        return binding.root
    }

    @DelicateCoroutinesApi
    private fun setupSimpleSpinner() {
        val arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cotigories,
            android.R.layout.simple_spinner_item,
        )

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerMovie.adapter = arrayAdapter

        binding.spinnerMovie.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                binding.movieRecView.layoutManager = GridLayoutManager(requireContext(), 4)
                binding.movieRecView.adapter = adapterTop
                when (position) {

                    0 -> {
                        viewModelTop.getTopRatedMovie()
                    }

                    1 -> {
                        viewModelTop.getPopularMovie()
                    }

                    2 -> {
                        viewModelTop.getUpcomingMovie()
                    }

                    3 -> {
                        viewModelTop.getNowPlayingMovie()
                    }

                }
                GlobalScope.launch(Dispatchers.IO) {
                    viewModelTop.paging.collectLatest(adapterTop::submitData)
                }

                viewModelTop.movieOfListTop.observe(viewLifecycleOwner) {
                    adapterTop.movieList = it.body()!!.movieList
                }

                adapterTop.addLoadStateListener { state: CombinedLoadStates ->
                    binding.movieRecView.isVisible = state.refresh != LoadState.Loading
                    binding.errorMoviePB.isVisible = state.refresh == LoadState.Loading
                }
                val selectedItem = parent!!.getItemAtPosition(position)
                Toast.makeText(requireContext(), "$selectedItem Selected", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Использовать по вашему желанию
            }

        }
    }
}