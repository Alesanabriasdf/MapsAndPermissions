package com.example.educacionitconsumoapimaps

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import android.media.AudioMetadata.createMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seePermission()

        createMap()

    }

    private fun createMap(){

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0

        createMarker()

    }

    private fun createMarker() {

        val location = LatLng(-31.420222, -64.188771)

        val marker = MarkerOptions().position(location).title("fuente del perdon! :D")

        map.addMarker(marker)

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location,17f),3000,null)



    }

    private fun seePermission() {
        if (permissionGranted()){
            continuarFlujoHabitual()
        } else {
            pedirPermisos()
        }
    }

    private fun continuarFlujoHabitual() {
        Toast.makeText(this,"los permisos entan concedidos", Toast.LENGTH_LONG).show()
    }

    private fun permissionGranted(): Boolean {
        // return ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED
        val permision: Boolean
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED){
            permision = true
            return permision
        } else {
            permision = false
            return permision
        }
    }

    private fun pedirPermisos() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA")){
            //ya pedimos el permiso antes pero el user los rechazo
            Toast.makeText(this,"activa el permiso desde los ajustes vos mismo...", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.CAMERA"),
                REQUEST_CODE_CAMERA)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            REQUEST_CODE_CAMERA -> {
                continuarFlujoHabitual()
            }
            else -> {
                //acepto otro permiso que no especificamos como opcion en nuestro when
            }
        }
    }


    companion object{
        const val REQUEST_CODE_CAMERA = 0
    }

}