package com.example.tunnig;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import yuku.ambilwarna.AmbilWarnaDialog;


public class Pintura extends AppCompatActivity {
    private Button btnColor;
    private TextView tvHexColor;
    private int currentColor = Color.RED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pintura);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        /// === Boton regreso===///
        Button btnBk = findViewById(R.id.regresar);
        btnBk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(Pintura.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });

        btnColor = findViewById(R.id.btnCol);
        tvHexColor =  findViewById(R.id.tvHexColor);

        btnColor.setOnClickListener(v -> {// Usamos el selector nativo de Android
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Pintura.this);
        builder.setTitle("Selecciona un color (ejemplo)");

        // Array de colores base
        String[] colores = {"Rojo", "Verde", "Azul", "Amarillo", "Negro"};
        int[] valores = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK};

        builder.setItems(colores, (dialog, which) -> {
            currentColor = valores[which];
            String hex = String.format("#%06X", (0xFFFFFF & currentColor));
            tvHexColor.setText(hex);
            tvHexColor.setTextColor(currentColor);
         });

            builder.show();
        });
}
}
