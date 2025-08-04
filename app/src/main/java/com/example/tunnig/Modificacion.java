package com.example.tunnig;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.util.*;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Modificacion extends AppCompatActivity {
    private TextView txtPlataforma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificacion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button eli = findViewById(R.id.btnEl);

        funciones funciones = new funciones();
        txtPlataforma =findViewById(R.id.txtPlata);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("plataformas").child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists()){
                    Plata002 plat = snapshot.getValue(Plata002.class);
                    if(plat != null){
                        String info = "PLataforma \n" + "Fabricante: " + plat.getFabricante()
                                + "\n" + "Modelo: " + plat.getModelo() + "\n" +
                                "Año: " + plat.getAño() + "\n" + "Edicion: " + plat.getEdiciones();
                        txtPlataforma.setText(info);
                    }
                }else{
                    txtPlataforma.setText("no tienes plataforma guardada");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txtPlataforma.setText("Error de carga");

            }
        });



        /// === Boton Modificacion===///
        eli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    DatabaseReference ref = FirebaseDatabase.getInstance()
                            .getReference("plataformas")
                            .child(userId);

                    ref.removeValue()
                            .addOnSuccessListener(unused -> Log.d("Firebase", "Plataforma eliminada"))
                            .addOnFailureListener(e -> Log.e("Firebase", "Error al eliminar", e));
                    intent = new Intent(Modificacion.this, Configuracion.class);
                    startActivity(intent);


                }
            }
        });


        /// === Boton regreso===///
        Button btnBk = findViewById(R.id.regresar);
        btnBk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(Modificacion.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });



    }
}