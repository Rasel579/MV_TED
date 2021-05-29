package com.example.mv_ted.ui.view.google_map

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
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
import com.example.mv_ted.expirements.GeofenceService
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.example.mv_ted.view_model.AppState
import com.example.mv_ted.view_model.MapsViewModel
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.*
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment(), ConnectionCallbacks {
    private var binding: FragmentMapsBinding?= null
    private lateinit var map : GoogleMap
    private lateinit var googleClient : GoogleApiClient
    private lateinit var geofenceClient : GeofencingClient
    private lateinit var geofence :Geofence
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
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
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



    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleClient = Builder(requireContext())
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .build()
        googleClient.connect()
        initGeofence()
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
        val movie : MovieResultDTO? = arguments?.getParcelable(MOVIE)
        movie?.id?.let { viewModel.getCountryOfFilm(it) }
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

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    map.isMyLocationEnabled = true
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initGeofence() {
        geofenceClient = LocationServices.getGeofencingClient(requireContext())
        geofence = Geofence.Builder().setRequestId("1")
            .setCircularRegion(CINEMA[cinemaGrandPlace]?.get(0)!!,
                CINEMA[cinemaGrandPlace]?.get(1)!!,
                RADIUS_GEOFENCE_REGION)
            .setCircularRegion(CINEMA[cinema7d]?.get(0)!!,
                CINEMA[cinema7d]?.get(1)!!,
                RADIUS_GEOFENCE_REGION)
            .setCircularRegion(CINEMA[cinemaHostel]?.get(0)!!,
                CINEMA[cinemaHostel]?.get(1)!!,
                RADIUS_GEOFENCE_REGION)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(GEOFENCE_TIME)
            .build()
        val geofenceRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()
        val geoService = Intent(context, GeofenceService :: class.java)
        val pendingIntent = PendingIntent
            .getService(context, 0, geoService, PendingIntent.FLAG_UPDATE_CURRENT)
        geofenceClient.addGeofences(geofenceRequest, pendingIntent).addOnCompleteListener{
                AlertDialog.Builder(context).setTitle(getString(R.string.Title_alert_geofence)).setMessage(getString(
                                    R.string.mesg_alert_geofence)).show()
            }

    }
    override fun onConnected(bundle: Bundle?) {
        Log.e("GoogleApi", "Connected")
    }

    override fun onConnectionSuspended(suspend: Int) {
    }

    companion object {
        const val MOVIE = "movie"
        const val REQUEST_CODE = 89
        const val GEOFENCE_TIME = 800000L
        const val RADIUS_GEOFENCE_REGION = 1000f
        private const val cinemaGrandPlace = "Cinema grand place"
        private const val cinemaHostel = "Cinema hostel"
        private const val cinema7d = "Cinema 7d"
         val CINEMA = mapOf(
             Pair(cinemaGrandPlace, arrayOf(59.9361,30.3336)),
             Pair(cinemaHostel, arrayOf(59.9225,30.3326)),
             Pair(cinema7d, arrayOf(59.9189,30.3389))
         )
        fun newInstance(bundle: Bundle) : MapsFragment{
            val fragment = MapsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
