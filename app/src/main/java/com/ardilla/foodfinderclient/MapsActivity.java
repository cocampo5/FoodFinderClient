package com.ardilla.foodfinderclient;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ardilla.foodfinderclient.model.VendedorDTO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<VendedorDTO> vendedorsList = new ArrayList<VendedorDTO>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        RequestJson rq = new RequestJson();
        rq.doQuery();
        vendedorsList = rq.getVendedorDTOList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        //Reivsar si tenemos el permiso
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            Log.i("InfoMyLocationButton", "Todo bien");
        }else{
            Log.i("InfoMyLocationButton", "Error");
        }

        LatLng eafit = new LatLng(6.199729, -75.578698);
        mMap.addMarker(new MarkerOptions().position(eafit));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eafit));
        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.75f));

    }
}
