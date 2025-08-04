package com.example.tunnig;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class config extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView tvFabricante = findViewById(R.id.Txfab);
        TextView tvModelo = findViewById(R.id.TxMod);
        TextView tvAño = findViewById(R.id.TxA);
        TextView tvEdicion = findViewById(R.id.TxEd);
        /// === Boton regreso===///
        Button btnBk = findViewById(R.id.regresar);
        btnBk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(config.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("plataformas")
                .child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Plata002 plat = snapshot.getValue(Plata002.class);

                    tvFabricante.setText("Fabricante: " + plat.getFabricante());
                    tvModelo.setText("Modelo: " + plat.getModelo());
                    tvAño.setText("Año: " + plat.getAño());
                    tvEdicion.setText("Edición: " + plat.getEdiciones());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(config.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
        });

    }
}