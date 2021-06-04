package com.example.mv_ted.ui.view.detail_fragment


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.mv_ted.R
import com.example.mv_ted.databinding.FragmentDetailBinding
import com.example.mv_ted.models.data.model.Comment
import com.example.mv_ted.models.data.model.Movie
import com.example.mv_ted.models.data.model.imageUri
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.example.mv_ted.ui.view.google_map.MapsFragment
import com.example.mv_ted.view_model.AppState
import com.example.mv_ted.view_model.DetailViewModel
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var _binding : FragmentDetailBinding
    private var movie : MovieResultDTO ?= null
    private var comments : MutableList<Comment> ?=  null
    private var likeImg = R.drawable.ic_baseline_tag_faces_24
    private val adapter: DetailFragmentAdapter by lazy { DetailFragmentAdapter() }
    private val viewModel : DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.bind(inflater.inflate(R.layout.fragment_detail, container, false))
        setHasOptionsMenu(true)
        return  _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)= with(_binding) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movie = arguments?.getParcelable(MOVIE_DATA)
        }

        movieOverview.text = movie?.overview
        movieIdName.text = movie?.originalTitle
        movieIdDate.text = movie?.releaseDate
        Picasso.get()
            .load(imageUri + movie?.posterPath)
            .fit()
            .into(movieImage)
        like.setOnClickListener {
            likeImg = if (likeImg == R.drawable.ic_baseline_tag_faces_24){
                R.drawable.ic_baseline_tag_faces_like_on
            } else{
                R.drawable.ic_baseline_tag_faces_24
            }
            like.setImageResource(likeImg)
            viewModel.saveLikesMovie(Movie(movie?.originalTitle,  movie?.releaseDate, movie?.posterPath, movie?.overview))
        }

        viewModel.detailLiveData.observe(viewLifecycleOwner, {
            renderData(it)
        })
        movie?.id.let {
            if (it != null) {
                viewModel.getAllCommentsByMovie(it)
            }
        }


        sentToDB.setOnClickListener {
            if(inputComments.text != null) movie?.id.let{ id ->
                if (id != null) {
                    viewModel
                        .saveEntity(inputComments.text.toString(), id)
                }
                inputComments.setText("")
            }
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            comments?.add(Comment(0,"0", inputComments.text.toString()))
            movie?.id?.let { it -> viewModel.getAllCommentsByMovie(it) }
        }
    }

    private fun renderData(it: AppState?)  {
       when(it){
         is AppState.SuccessDetailsFrg -> {
             comments = it.commentsList.toMutableList()
             if (comments != null){
                 adapter.setData(it.commentsList)
                 adapter.notifyDataSetChanged()
                 initRecycleView()
             }

         }
       }
    }

    private fun initRecycleView()= with(_binding) {
        val recyclerView = MovieComments
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.to_google_map -> {
               val manger = activity?.supportFragmentManager
                manger?.let {
                    val bundle = Bundle().apply {
                        putParcelable(MapsFragment.MOVIE, movie)
                    }
                    manger.beginTransaction()
                        .replace(R.id.content_main_frame, MapsFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        }
        return super.onOptionsItemSelected(item)
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