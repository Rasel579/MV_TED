package com.movieapp.mv_ted.presentation.detail


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.movieapp.mv_ted.R
import com.movieapp.mv_ted.databinding.FragmentDetailBinding
import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.domain.models.response.MovieResponse
import com.movieapp.mv_ted.presentation.detail.adapter.DetailFragmentAdapter
import com.movieapp.mv_ted.presentation.maps.MapsFragment
import com.movieapp.mv_ted.domain.AppState
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.models.data.model.imageUri
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var _binding : FragmentDetailBinding
    private val movieId: Int? by lazy {
        arguments?.getInt(MOVIE_ID)
    }
    private var movie : MovieResponse? =  null
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
        viewModel.detailLiveData.observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.getMovieById(movieId.toString())
    }

    private fun initView(movie: MovieResponse?)= with(_binding) {
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
        movie?.id.let {
            if (it != null) {
                viewModel.getAllCommentsByMovie(movieId.toString())
            }
        }
        sentToDB.setOnClickListener {
            if(inputComments.text != null) movie?.id.let{ id ->
                if (id != null) {
                    viewModel
                        .saveEntity(inputComments.text.toString(), id.toString())
                }
                inputComments.setText("")
            }
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            comments?.add(Comment(0,"0", inputComments.text.toString()))
            movie?.id?.let { it -> viewModel.getAllCommentsByMovie(it.toString()) }
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
           is AppState.SuccessMovieId -> {
               movie = it.movie
               initView(movie)
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
                        movie?.id?.let { it1 -> putInt(MapsFragment.MOVIE_ID, it1) }
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
        const val MOVIE_ID = "movie_id"
        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}