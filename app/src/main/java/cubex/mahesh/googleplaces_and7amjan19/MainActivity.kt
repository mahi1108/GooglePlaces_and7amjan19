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
import com.google.android.gms.location.places.Place
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.location.places.ui.PlacePicker



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
