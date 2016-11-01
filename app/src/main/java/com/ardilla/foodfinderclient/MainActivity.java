package com.ardilla.foodfinderclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ardilla.foodfinderclient.controller.AppController;
import com.ardilla.foodfinderclient.model.Vendedor;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = RequestJson.class.getSimpleName();
    private static final String url = "http://104.196.212.66:3000/vendedors.json";
    public List<Vendedor> VendedorList;

    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doQuery();
                if(VendedorList.isEmpty() || VendedorList.equals(null)){
                    Toast.makeText(getApplicationContext(), "Servidor Apagado", Toast.LENGTH_SHORT).show();
                }
                else{
                    for(Vendedor vend: VendedorList){
                        Toast.makeText(getApplicationContext(),  vend.getNombre()+" "+vend.getApellido(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }


    public void doQuery(){
        VendedorList = new ArrayList<>();
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
                                Toast.makeText(getApplicationContext(), vendedor.getNombre(), Toast.LENGTH_SHORT).show();
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
