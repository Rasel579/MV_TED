package com.example.mv_ted.ui.view.detail_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.example.mv_ted.R
import com.example.mv_ted.databinding.FragmentDetailBinding
import com.example.mv_ted.models.data.model.Movie
import com.example.mv_ted.models.data.model.imageUri
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.example.mv_ted.view_model.AppState
import com.example.mv_ted.view_model.DetailViewModel
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var _binding : FragmentDetailBinding
    private val adapter: DetailFragmentAdapter by lazy { DetailFragmentAdapter() }
    private val viewModel : DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.bind(inflater.inflate(R.layout.fragment_detail, container, false))
        return  _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)= with(_binding) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<MovieResultDTO>(MOVIE_DATA)
        movieOverview.text = movie?.overview
        movieIdName.text = movie?.originalTitle
        movieIdDate.text = movie?.releaseDate
        Picasso.get()
            .load(imageUri + movie?.posterPath)
            .fit()
            .into(movieImage)
        like.setOnClickListener {
            like.setImageResource(R.drawable.ic_baseline_tag_faces_like_on)
            if (movie != null){
                viewModel.saveLikesMovie(Movie(movie.originalTitle,movie.releaseDate, movie.posterPath))
            }
        }
        sentToDB.setOnClickListener {
             if(inputComments.text != null) movie?.id?.let{ id -> viewModel
                 .saveEntity(inputComments.text.toString(), id) }
         }
        viewModel.detailLiveData.observe(viewLifecycleOwner, {
            renderData(it)
        })
        if (movie != null) {
            viewModel.getAllCommentsByMovie(movie.id)
        }
    }

    private fun renderData(it: AppState?)  {
       when(it){
         is AppState.SuccessDetailsFrg -> {
             adapter.setData(it.commentsList)
             adapter.notifyDataSetChanged()
             initRecycleView()
         }
       }
    }

    private fun initRecycleView()= with(_binding) {
        val recyclerView = MovieComments
        recyclerView.adapter = adapter
    }

    companion object {
        const val MOVIE_DATA = "movie_details"
        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}