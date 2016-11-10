package com.ardilla.foodfinderclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    private String nombre, apellido, correo, pass, celular;
    private EditText n, a, e, p, pp, cel;

    public RegistroActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        n = (EditText) findViewById(R.id.editText10);
        a = (EditText) findViewById(R.id.editText11);
        cel = (EditText) findViewById(R.id.editText12);
        e = (EditText) findViewById(R.id.editText13);
        p = (EditText) findViewById(R.id.editText14);
        pp = (EditText) findViewById(R.id.editText15);

        Button button = (Button) findViewById(R.id.btn_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p.getText().toString().equals(pp.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Son iguales",
                            Toast.LENGTH_LONG).show();
                            postearUser(n.getText().toString(),a.getText().toString(),cel.getText().toString()
                                    ,e.getText().toString(),p.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "No son iguales",
                            Toast.LENGTH_LONG).show();
                }
                //Intent registro = new Intent().setClass(
                //        RegistroActivity.this, MapsActivity.class);
                //startActivity(registro);
                //finish();
            }
        });

        Button button2 = (Button) findViewById(R.id.btn_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void postearUser(String nombre,String apellido,String celular,String email,String pass) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://104.196.212.66:3000/clientes";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("nombre", nombre);
            jsonBody.put("apellido", apellido);
            jsonBody.put("celular", celular);
            jsonBody.put("email", email);
            jsonBody.put("pass", pass);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
