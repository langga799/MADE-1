package com.langga.movieapp.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.langga.movieapp.R
import com.langga.movieapp.core.data.source.Resource
import com.langga.movieapp.core.ui.MovieAdapter
import com.langga.movieapp.databinding.FragmentHomeBinding
import com.langga.movieapp.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MovieAdapter()
        binding.apply {
            rvMovie.layoutManager = GridLayoutManager(requireActivity(), 2)
            rvMovie.setHasFixedSize(true)
            rvMovie.adapter = adapter

            rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val navigation = activity?.findViewById<BottomNavigationView>(R.id.nav_view)!!
                    when {
                        dy > 0 && navigation.isShown -> {
                            navigation.visibility = View.GONE
                        }
                        dy < 0 -> {
                            navigation.visibility = View.VISIBLE
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }

            })
        }


        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> print("loading")
                    is Resource.Success -> adapter.setDataMovies(movie.data)
                    is Resource.Error -> print("error")
                }
            }
        }


        adapter.onItemClick = { clickItem ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, clickItem)
            startActivity(intent)
        }
    }


}