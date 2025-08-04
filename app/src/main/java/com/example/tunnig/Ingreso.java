package com.example.tunnig;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.*;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Ingreso extends AppCompatActivity {

    private EditText editCor, editCont;
    private Button btnI;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ingreso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editCont = findViewById(R.id.Cont);
        editCor = findViewById(R.id.Cor);
        btnI = findViewById(R.id.BtnIng);

        mAuth = FirebaseAuth.getInstance(); //// == conexion con fire base == ////

        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = editCor.getText().toString().trim();
                String contra = editCont.getText().toString().trim();
                if(correo.isEmpty() || contra.isEmpty()){
                    Toast.makeText(Ingreso.this, "Llena los campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(correo, contra)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(Ingreso.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Ingreso.this, MenuPrincipal.class));
                                finish();
                            }else {
                                Toast.makeText(Ingreso.this, "correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });
    }
}