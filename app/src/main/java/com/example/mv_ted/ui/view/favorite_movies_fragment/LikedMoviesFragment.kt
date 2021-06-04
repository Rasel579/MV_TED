package com.example.mv_ted.ui.view.favorite_movies_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mv_ted.databinding.FragmentLikedMoviesBinding
import com.example.mv_ted.view_model.AppState
import com.example.mv_ted.view_model.LikedMovieViewModel


class LikedMoviesFragment : Fragment() {
    private lateinit var binding : FragmentLikedMoviesBinding
    private val adapter : LikedMovieAdapter by lazy { LikedMovieAdapter() }
    private val viewModel :LikedMovieViewModel by lazy {
        ViewModelProvider(this).get(LikedMovieViewModel ::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLikedMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner, {render(it)})
        viewModel.getAllMovies()
    }

    private fun render(it: AppState?) {
        when(it){
           is AppState.SuccessLike -> {
                adapter.setData(it.likedMoviesList)
                initRecycleView()
            }
        }

    }
    private fun initRecycleView()= with(binding) {
        val recyclerView = likesMoviesRecView
        recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance() = LikedMoviesFragment()
    }
}