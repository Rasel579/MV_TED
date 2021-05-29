package com.example.mv_ted.ui.view.google_map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mv_ted.R
import com.example.mv_ted.databinding.FragmentMapsBinding
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.example.mv_ted.view_model.AppState
import com.example.mv_ted.view_model.MapsViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment() {
    private var binding: FragmentMapsBinding?= null
    private lateinit var map : GoogleMap
    private val markers: ArrayList<Marker> = ArrayList()
    private val viewModel : MapsViewModel by lazy { 
        ViewModelProvider(this).get(MapsViewModel::class.java)
    }
    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        if(ActivityCompat.checkSelfPermission(
             requireContext(),
             Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED){
              map.isMyLocationEnabled = true
        }
        val initialPlace = LatLng(60.43,50.34)
        val marker = googleMap.addMarker(MarkerOptions().position(initialPlace).title(getString(R.string.Start_position)))
        marker.let { markers.add(it)}
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(initialPlace))
        googleMap.setOnMapLongClickListener {latLng ->
                getAddressAsync(latLng)
                setMarker(latLng, "")
                drawLine()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.bind(inflater.inflate(R.layout.fragment_maps, container, false))
        viewModel.liveData.observe(viewLifecycleOwner, {
            renderData(it)
        })
        val movie : MovieResultDTO = arguments?.getParcelable(Movie)!!
        viewModel.getCountryOfFilm(movie.id)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initSearchByAddress()
    }

    private fun getAddressAsync(latLng: LatLng) {
         context?.let {
           val geocoder = Geocoder(it)
           Thread {
               try {
                   val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                   textAddress.post{
                       textAddress.text = address[0].getAddressLine(0)
                   }

               } catch (e: Exception) {
                   e.printStackTrace()
               }
           }.start()
         }
    }
    private fun drawLine() {
        val last : Int = markers.size - 1
        if (last >= 1){
             val previousPos = markers[last - 1].position
             val currentPos = markers[last].position
              map.addPolyline(
                  PolylineOptions()
                      .add(previousPos, currentPos)
                      .color(Color.RED)
                      .width(5f)
              )
        }
    }

    private fun initSearchByAddress(){
          btnSearch.setOnClickListener{
            val geoCoder = Geocoder(it.context)
            val searchText = searchAddress.text.toString()
            Thread{
                try {
                    val address = geoCoder.getFromLocationName(searchText,1)
                    if (address.isNotEmpty()){
                        goToAddress(it, address, searchText)
                    }
                } catch (e : Exception){
                    e.printStackTrace()
                }

            }.start()
          }
    }

    private fun renderData(it: AppState?) {
        when(it){
            is AppState.SuccessFilmCountry ->{
                Log.e("Frg", it.country)
                val geocoder = Geocoder(requireContext())
                Thread{
                    try {
                        val address = geocoder.getFromLocationName(it.country.trim(), 2)
                        goToAddress(view, address, it.country)
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                }.start()

            }
        }

    }

    private fun goToAddress(view: View?, address: List<Address>, searchText: String) {
           val location = LatLng(address[0].latitude, address[0].longitude)
           view?.post {
                setMarker(location, searchText)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
           }

    }

    private fun setMarker(location: LatLng, searchText: String) {
            val marker = map.addMarker(MarkerOptions()
                .position(location)
                .title(searchText))
        marker.let {
            markers.add(marker)
        }

    }

    companion object {
        const val Movie = "movie"
        fun newInstance(bundle: Bundle) : MapsFragment{
            val fragment = MapsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
