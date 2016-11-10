package com.ardilla.foodfinderclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ardilla.foodfinderclient.controller.AppController;
import com.ardilla.foodfinderclient.model.VendedorDTO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = RequestJson.class.getSimpleName();
    private static final String url = "http://104.196.212.66:3000/vendedors.json";
    private List<VendedorDTO> vendedorDTOList = new ArrayList<VendedorDTO>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Creating volley request obj
        JsonArrayRequest vendedorReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                VendedorDTO vendedorDTO = new VendedorDTO();
                                vendedorDTO.setNombre(obj.getString("nombre"));
                                vendedorDTO.setApellido(obj.getString("apellido"));
                                vendedorDTO.setCelular(obj.getInt("celular"));
                                vendedorDTO.setLatitud(((Number) obj.get("latitud"))
                                        .doubleValue());
                                vendedorDTO.setLongitud(((Number) obj.get("longitud"))
                                        .doubleValue());
                                vendedorDTO.setEmail(obj.getString("email"));
                                vendedorDTOList.add(vendedorDTO);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        refreshMap();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(vendedorReq, TAG);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        //Revisar si tenemos el permiso
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        LatLng eafit = new LatLng(6.199729, -75.578698);
        mMap.addMarker(new MarkerOptions().position(eafit));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(eafit));
        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.75f));

    }

    /**
     * Este método hace un request de permisos (API 23+)
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        mMap.setMyLocationEnabled(true);
                    }catch (SecurityException se){
                        se.printStackTrace();
                    }
                } else {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void refreshMap() {
        for (int i = 0; i < vendedorDTOList.size(); i++) {
            String name = vendedorDTOList.get(i).getNombre();
            String apellido = vendedorDTOList.get(i).getApellido();
            double lat = vendedorDTOList.get(i).getLatitud();
            double lon = vendedorDTOList.get(i).getLongitud();
            Log.i("HOLI", name+" "+ apellido);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(name+" "+apellido));
        }
    }

}
