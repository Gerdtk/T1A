package com.example.tunnig;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
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

import java.util.Random;
public class partes extends AppCompatActivity {
    private RequestQueue queue;
    private ImageView imageView, imageView02, imageView03;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_partes);

        imageView = findViewById(R.id.img01);
        imageView02 = findViewById(R.id.img02);
        imageView03 = findViewById(R.id.img03);
        queue = Volley.newRequestQueue(this);
        progressBar = findViewById(R.id.progressBar);

        /// === Boton regreso===///
        Button btnBk = findViewById(R.id.regresar);
        btnBk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(partes.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });

        //RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.unsplash.com/search/photos?page=" +
                (1 + new Random().nextInt(3)) + "&order_by=latest"
                +"&query=" + "Enkei"
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
        String url01 = "https://api.unsplash.com/search/photos?page=" +
                (1 + new Random().nextInt(3)) + "&order_by=latest"
                +"&query=" + "Aleron_Mugen"
                + "&client_id=GJfaaS0v5PusN6_crQl3rcL823DBZ9VO0haE0R7yUYs";

        JsonObjectRequest request01 = new JsonObjectRequest(Request.Method.GET, url01, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        if (results.length() > 0) {
                            JSONObject photo = results.getJSONObject(0);
                            String imageUrl = photo.getJSONObject("urls").getString("regular");
                            Log.d("Unsplash", "imagen: " + imageUrl);
                            if(imageView02 != null){
                                Glide.with(imageView02.getContext())
                                        .load(imageUrl)
                                        .into(imageView02);


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
        String url03 = "https://api.unsplash.com/search/photos?page=" +
                (1 + new Random().nextInt(3)) + "&order_by=latest"
                +"&query=" + "Tires"
                + "&client_id=GJfaaS0v5PusN6_crQl3rcL823DBZ9VO0haE0R7yUYs";

        JsonObjectRequest request03 = new JsonObjectRequest(Request.Method.GET, url03, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        if (results.length() > 0) {
                            JSONObject photo = results.getJSONObject(0);
                            String imageUrl = photo.getJSONObject("urls").getString("regular");
                            Log.d("Unsplash", "imagen: " + imageUrl);
                            if(imageView03 != null){
                                Glide.with(imageView03.getContext())
                                        .load(imageUrl)
                                        .into(imageView03);


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
        queue.add(request01);
        queue.add(request03);

    }
}