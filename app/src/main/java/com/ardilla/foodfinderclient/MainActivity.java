package com.ardilla.foodfinderclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Botón 1",
                //        Toast.LENGTH_LONG).show();

                Intent registro = new Intent().setClass(
                        MainActivity.this, RegistroActivity.class);
                startActivity(registro);
                //finish();
            }
        });

        Button button2 = (Button) findViewById(R.id.btn_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Login",
                //        Toast.LENGTH_LONG).show();
                Intent login = new Intent().setClass(
                        MainActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }


}
