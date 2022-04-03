package com.langga.movieapp.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.langga.movieapp.core.ui.MovieAdapter
import com.langga.movieapp.databinding.FragmentSearchBinding
import com.langga.movieapp.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel : SearchViewModel by viewModel()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchMovie.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery(newText.orEmpty())
                return true
            }

        })

        movieAdapter = MovieAdapter()
        binding.rvSearchMovie.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = movieAdapter
            setHasFixedSize(true)
        }

    }


    private fun searchQuery(text: String?){
        if (text != null) {
            viewModel.searchMovie(text).observe(viewLifecycleOwner){ resultSearching ->
                movieAdapter.setDataMovies(resultSearching)
                Log.d("search", text.toString())
                Log.d("search", resultSearching.toString())


            }
        }

        movieAdapter.onItemClick = { clickItem ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, clickItem)
            startActivity(intent)
        }
    }


}

