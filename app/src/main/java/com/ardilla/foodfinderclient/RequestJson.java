package com.ardilla.foodfinderclient;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ardilla.foodfinderclient.model.Vendedor;
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
    private static final String url = "http://ec2-54-148-64-153.us-west-2.compute.amazonaws.com:3000/vendedors.json";
    private List<Vendedor> VendedorList = new ArrayList<Vendedor>();

    public RequestJson(){

    }

    public List<Vendedor> doQuery(){
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
                                Vendedor vendedor = new Vendedor();
                                vendedor.setNombre(obj.getString("nombre"));
                                vendedor.setApellido(obj.getString("apellido"));
                                vendedor.setCelular(obj.getInt("celular"));
                                vendedor.setLatitud(((Number) obj.get("latitud"))
                                        .doubleValue());
                                vendedor.setLongitud(((Number) obj.get("longitud"))
                                        .doubleValue());
                                vendedor.setEmail(obj.getString("email"));
                                VendedorList.add(vendedor);
                                //Toast.makeText(getApplicationContext(), vendedor.getNombre(), Toast.LENGTH_SHORT).show();
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
        return VendedorList;
    }
}
