package com.ardilla.foodfinderclient;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ardilla.foodfinderclient.model.VendedorDTO;
import com.ardilla.foodfinderclient.controller.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cristobal on 16/10/16.
 */

public class RequestJson {

    private static final String TAG = RequestJson.class.getSimpleName();
    private static final String url = "http://104.196.212.66:3000/vendedors.json";
    private List<VendedorDTO> vendedorDTOList = new ArrayList<VendedorDTO>();

    public void doQuery(){
        vendedorDTOList = new ArrayList<>();
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
}
