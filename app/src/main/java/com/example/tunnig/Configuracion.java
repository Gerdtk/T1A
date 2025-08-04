package com.example.tunnig;

import android.content.Intent;
import android.os.Bundle;

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

import android.util.Log;
import android.widget.*;
import java.util.*;
import android.view.*;

import java.util.List;



public class Configuracion extends AppCompatActivity {

            private Spinner spFabricante, spModelo, spAño, spEdicion;
            private DatabaseReference ref;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                ref = FirebaseDatabase.getInstance().getReference("catalogo");



                try {
                    setContentView(R.layout.activity_configuracion);

                    // inicializar spinners
                    spFabricante = findViewById(R.id.spinfab);
                    spModelo = findViewById(R.id.spinMod);
                    spAño = findViewById(R.id.spinA);
                    spEdicion = findViewById(R.id.SpinEd);

                    // cargar datos
                    cargarFabricantes();

                } catch (Exception e) {
                    Log.e("CONFIG_ERROR", "Error en onCreate: ", e);
                    Toast.makeText(this, "Error al abrir Configuración", Toast.LENGTH_SHORT).show();
                }



                Button btnG = findViewById(R.id.btnG02);
                btnG.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        String fabricante = spFabricante.getSelectedItem().toString().trim();
                        String modelo = spModelo.getSelectedItem().toString().trim();
                        String año = spAño.getSelectedItem().toString().trim();
                        String edicion = spEdicion.getSelectedItem().toString().trim();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user == null) {
                            // El usuario no está logueado → regresar al login
                            startActivity(new Intent(Configuracion.this, MainActivity.class));
                            finish();
                            return;
                        }

                        String uid = user.getUid();

                        Plata002 plat = new Plata002(fabricante, modelo, año, edicion);

                        FirebaseDatabase.getInstance().getReference("plataformas")
                                .child(uid)
                                .setValue(plat)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Configuracion.this, "Plataforma guardada", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Configuracion.this, MenuPrincipal.class);
                                        startActivity(intent);
                                        finish();// o redirigir
                                    } else {
                                        Toast.makeText(Configuracion.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                                    }
                                });




                    }


                });

                /// === Boton regreso===///
                Button btnBk = findViewById(R.id.regresar);
                btnBk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        intent = new Intent(Configuracion.this, MenuPrincipal.class);
                        startActivity(intent);
                    }
                });



            }

            // Fabricantes
            private void cargarFabricantes() {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> fabricantes = new ArrayList<>();

                        for (DataSnapshot fabricanteSnap : snapshot.getChildren()) {
                            fabricantes.add(fabricanteSnap.getKey());
                        }

                        ArrayAdapter<String> adapterFabricantes = new ArrayAdapter<>(
                                Configuracion.this,
                                android.R.layout.simple_spinner_item,
                                fabricantes
                        );
                        adapterFabricantes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spFabricante.setAdapter(adapterFabricantes);

                        // 4️⃣ Listener para cargar modelos cuando se elija un fabricante
                        spFabricante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String fabricanteSeleccionado = parent.getItemAtPosition(position).toString();
                                cargarModelos(fabricanteSeleccionado);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Configuracion.this, "Error cargando fabricantes", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // Modelos
            private void cargarModelos(String fabricanteSeleccionado) {
                ref.child(fabricanteSeleccionado).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> modelos = (List<String>) snapshot.child("modelo").getValue();

                        if (modelos != null) {
                            ArrayAdapter<String> adapterModelos = new ArrayAdapter<>(
                                    Configuracion.this,
                                    android.R.layout.simple_spinner_item,
                                    modelos
                            );
                            adapterModelos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spModelo.setAdapter(adapterModelos);

                            // Listener para cargar años cuando se elija modelo
                            spModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    cargarAños(snapshot);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {}
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Configuracion.this, "Error cargando modelos", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // Años
            private void cargarAños(DataSnapshot snapshotFabricante) {
                List<String> años = (List<String>) snapshotFabricante.child("año").getValue();

                if (años != null) {
                    ArrayAdapter<String> adapterAños = new ArrayAdapter<>(
                            Configuracion.this,
                            android.R.layout.simple_spinner_item,
                            años
                    );
                    adapterAños.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spAño.setAdapter(adapterAños);

                    // Listener para cargar ediciones
                    spAño.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cargarEdiciones(snapshotFabricante);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }
            }

            // Ediciones
            private void cargarEdiciones(DataSnapshot snapshotFabricante) {
                List<String> edicion = (List<String>) snapshotFabricante.child("ediciones").getValue();

                if (edicion != null) {
                    ArrayAdapter<String> adapterEdicion = new ArrayAdapter<>(
                            Configuracion.this,
                            android.R.layout.simple_spinner_item,
                            edicion
                    );
                    adapterEdicion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spEdicion.setAdapter(adapterEdicion);
                }
            }


        }



