package cubex.mahesh.googleplaces_and7amjan19

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import com.google.android.gms.location.places.Place
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var places_list:List<ResultsItem>? = null

class MainActivity : AppCompatActivity() {
  // AIzaSyDdCGdR2cnWw0AB0LeN3jOTjKmkKag4tew
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      s1.setOnSeekBarChangeListener(
          object : SeekBar.OnSeekBarChangeListener {
              override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {

                  svalue.text = progress.toString()

                 
              }

              override fun onStartTrackingTouch(p0: SeekBar?) {
              }

              override fun onStopTrackingTouch(p0: SeekBar?) {
              }
          })

        getCurrentLocation()

        placePicker.setOnClickListener {
            val builder = PlacePicker.IntentBuilder()
            startActivityForResult(builder.build(this), 11)
        }

        getPlaces.setOnClickListener {
            var r: Retrofit = Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create()).
                baseUrl("https://maps.googleapis.com/").build()

            var api = r.create(PlacesAPI::class.java)
            var call:Call<Places> = api.getPlaces(
                    "${tv_lati.text.toString()},${tv_longi.text.toString()}",
                    sp1.selectedItem.toString(),
                    svalue.text.toString().toInt())
            call.enqueue(object:Callback<Places>{
                override fun onResponse(call: Call<Places>, response: Response<Places>) {
                        var p:Places? = response.body()
                       places_list = p?.results

                     Toast.makeText(this@MainActivity,
                                    places_list?.size.toString(),
                                Toast.LENGTH_LONG).show()

                    var fManager = supportFragmentManager
                    var tx = fManager.beginTransaction()
                    tx.replace(R.id.frame1,ListOfPlaces())
                    tx.commit()

                }

                override fun onFailure(call: Call<Places>, t: Throwable) {

                    Toast.makeText(this@MainActivity,
                        "Fail to get Response",
                        Toast.LENGTH_LONG).show()

                }
            })


        }


       list.setOnClickListener {
           var fManager = supportFragmentManager
           var tx = fManager.beginTransaction()
           tx.replace(R.id.frame1,ListOfPlaces())
           tx.commit()
       }

      map.setOnClickListener {
          var sm = SupportMapFragment()
          var fManager = supportFragmentManager
          var tx = fManager.beginTransaction()
          tx.replace(R.id.frame1,sm)
          tx.commit()

          sm.getMapAsync {

              var mylatlng = LatLng(tv_lati.text.toString().toDouble(),
                  tv_longi.text.toString().toDouble() )
              var opt1 = MarkerOptions( )
              opt1.position(mylatlng)
              opt1.title("Current Location")
              opt1.icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN
              ))
              it.animateCamera(CameraUpdateFactory.
                        newLatLngZoom(mylatlng,15f))
              it.addMarker(opt1)



              for( item in places_list!!)
              {
                  var latlng = LatLng(item.geometry.location.lat,
                                                item.geometry.location.lng)
                  var opt1 = MarkerOptions( )
                  opt1.position(latlng)
                  opt1.title(item.name)
                  it.addMarker(opt1)
              }
          }


      }

    }  // onCreate( )


    @SuppressLint("MissingPermission")
    fun getCurrentLocation( )
    {
        var lManager : LocationManager = getSystemService(
            Context.LOCATION_SERVICE) as LocationManager
        lManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            1000L,1F,
            object:LocationListener{
                override fun onLocationChanged(loc: Location?) {
                    tv_lati.text = loc?.latitude.toString()
                    tv_longi.text = loc?.longitude.toString()
               //     lManager.removeUpdates(this)
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                }

                override fun onProviderEnabled(p0: String?) {
                }

                override fun onProviderDisabled(p0: String?) {
                }

            }
        )
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            var p: Place = PlacePicker.getPlace(
                this@MainActivity, data)
            tv_lati.text = p?.latLng.latitude.toString()
            tv_longi.text = p?.latLng.longitude.toString()
        }
    }

} // MainActivity
