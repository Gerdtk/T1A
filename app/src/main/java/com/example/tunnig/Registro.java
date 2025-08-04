package com.example.tunnig;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {
    EditText Nom, Cor, Cont;
    Button btnGu;
    funciones funciones;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        funciones = new funciones();
        btnGu = findViewById(R.id.BtnGuardar);
        Nom = findViewById(R.id.Nom);
        Cor = findViewById(R.id.Cor);
        Cont = findViewById(R.id.Cont);
        mAuth = FirebaseAuth.getInstance();


        btnGu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = Cor.getText().toString().trim();
                String contraseña = Cont.getText().toString().trim();
                String Nombre = Nom.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(correo, contraseña)
                        .addOnCompleteListener(task ->{
                            if (task.isSuccessful()){
                                String uid = mAuth.getCurrentUser().getUid();

                                Res nuevo = new Res(uid, Nombre);
                                funciones.insertar(nuevo);

                                Toast.makeText(Registro.this, "registrado", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registro.this, MenuPrincipal.class));
                                finish();
                            }else {
                                Toast.makeText(Registro.this, "Error al registrar: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}