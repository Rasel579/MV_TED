package com.example.mv_ted.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mv_ted.R
import com.example.mv_ted.databinding.FragmentDetailBinding
import com.example.mv_ted.models.data.model.imageUri
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var _binding : FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
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