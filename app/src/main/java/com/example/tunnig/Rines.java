package com.example.tunnig;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Rines extends AppCompatActivity {
    private RequestQueue queue;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rines);

        queue = Volley.newRequestQueue(this);
        imageView = findViewById(R.id.img00);
        RadioGroup rg = findViewById(R.id.grBtn);
        Button btnG = findViewById(R.id.btnEl);
        Button btnM = findViewById(R.id.btnMo);
        /// ///////////////////////////////////////

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String plataformaKey = " ";

        DatabaseReference opinRef = FirebaseDatabase.getInstance()
                .getReference("opiniones")
                .child(uid)
                .child(plataformaKey);


        /// === Boton regreso===///
        Button btnBk = findViewById(R.id.regresar);
        btnBk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(Rines.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });

        String url = "https://api.unsplash.com/search/photos?page=" +
                (1 + new Random().nextInt(3)) + "&order_by=latest"
                +"&query=" + "TSW + Rotary"
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

                },
                error -> {
                    Log.e("Unsplash", "Error: " + error.getMessage());
                }
        );

        opinRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snap) {
                boolean existe = snap.exists();
                rg.setEnabled(!existe);
                btnG.setVisibility(existe ? View.GONE : View.VISIBLE);
                btnM.setVisibility(existe ? View.VISIBLE : View.GONE);

                if (existe) {
                    String opcion = snap.child("opcion").getValue(String.class);
                    if ("Bueno".equals(opcion)) rg.check(R.id.rbtB);
                    else if ("Regular".equals(opcion)) rg.check(R.id.rbtR);
                    else if ("Malo".equals(opcion)) rg.check(R.id.rbtM);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });

        btnG.setOnClickListener(v -> {
            int checked = rg.getCheckedRadioButtonId();
            if (checked == -1) { Toast.makeText(this,"Selecciona una opción",Toast.LENGTH_SHORT).show(); return; }
            String opcion = ((RadioButton)findViewById(checked)).getText().toString();

            Map<String,Object> data = new HashMap<>();
            data.put("uid", uid);
            data.put("plataforma", plataformaKey);
            data.put("opcion", opcion);
            data.put("timestamp", System.currentTimeMillis());

            opinRef.setValue(data).addOnCompleteListener(t -> {
                if (t.isSuccessful()) {
                    Toast.makeText(this,"Opinión guardada",Toast.LENGTH_SHORT).show();
                    rg.setEnabled(false);
                    btnG.setVisibility(View.GONE);
                    btnM.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(this,"Error al guardar",Toast.LENGTH_SHORT).show();
                }
            });
        });
        btnM.setOnClickListener(v -> {
            rg.setEnabled(true);
            btnM.setVisibility(View.GONE);
            btnG.setVisibility(View.VISIBLE);
        });

    }

}