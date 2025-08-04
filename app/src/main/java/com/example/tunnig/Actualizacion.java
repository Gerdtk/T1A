package com.example.tunnig;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.net.Uri;

import java.util.Random;


public class Actualizacion extends AppCompatActivity {

    private RequestQueue queue;
    private ImageView imageView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizacion);
        imageView = findViewById(R.id.imgUno);
        queue = Volley.newRequestQueue(this);
        progressBar = findViewById(R.id.progressBar);

        /// === Boton regreso===///
        Button btnBk = findViewById(R.id.regresar);
        btnBk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(Actualizacion.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        cargarplataforma();
    }


    private void cargarplataforma(){
        progressBar.setVisibility(View.VISIBLE);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("plataformas")
                .child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot child) {

                if (child.exists()) {
                    String fabricante = child.child("fabricante").getValue(String.class);
                    String modelo = child.child("modelo").getValue(String.class);
                    String año = child.child("año").getValue(String.class);
                    String ediciones = child.child("ediciones").getValue(String.class);

                    String plataforma = Uri.encode(fabricante );

                    buscarU(plataforma);
                    Log.d("plata", "cargado " + plataforma);
                }else{
                    Log.e("plata", "No existe");
                    progressBar.setVisibility((View.GONE));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error:" + error.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void buscarU(String plataforma) {

        //RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.unsplash.com/search/photos?page=" +
                (1 + new Random().nextInt(3)) + "&order_by=latest"
                +"&query=" + plataforma
                + "&client_id=GJfaaS0v5PusN6_crQl3rcL823DBZ9VO0haE0R7yUYs";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        if (results.length() > 0) {
                            JSONObject photo = results.getJSONObject(0);
                            String imageUrl = photo.getJSONObject("urls").getString("regular");
                            Log.d("Unsplash", "imagen: " + imageUrl);
                           if(imageView != null){
                                Glide.with(imageView.getContext())
                                        .load(imageUrl)
                                        .into(imageView);
                            } else {
                                Log.e("glide", "imagen es null");
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.GONE);
                },
                error -> {
                    Log.e("Unsplash", "Error: " + error.getMessage());
                    progressBar.setVisibility(View.GONE);
                }
        );

        queue.add(request);
    }
}


