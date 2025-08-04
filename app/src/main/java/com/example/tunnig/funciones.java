package com.example.tunnig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.*;

public class funciones {

    private DatabaseReference databaseRef;
    private DatabaseReference databaseRe;

    /// === inicio de base de datos === ///
    public funciones(){
        databaseRef = FirebaseDatabase.getInstance().getReference("res");
    }
    public void insertar(Res nuevoRes){
        databaseRef.child(nuevoRes.getUid()).setValue(nuevoRes);


    }
    public void insertarCon(Context context){
        try{
            InputStream is = context.getAssets().open("fabricante.json");
            int size = is.available();
            byte[]buffer = new byte[size];
            is.read(buffer);
            is.close();
            /// ///
            String json = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(json);
            JSONArray fabricantes = obj.getJSONArray("fabricantes");

            DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("catalogo");
            for (int i =0; i < fabricantes.length(); i++){
                JSONObject fab = fabricantes.getJSONObject(i);

                String nombre = fab.getString("fabricante");
                JSONArray modelo = fab.getJSONArray("modelo");
                JSONArray año = fab.getJSONArray("year");
                JSONArray edicion = fab.getJSONArray("edicion");

                Map<String, Object> entry = new HashMap<>();
                entry.put("modelo", jsonArrayToList(modelo));
                entry.put("año", jsonArrayToList(año));
                entry.put("ediciones", jsonArrayToList(edicion));

                ref.child(nombre).setValue(entry, "DATO CARGADO");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private List<String> jsonArrayToList(JSONArray arr) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++){
            list.add(arr.getString(i));
        }
        return list;
    }

    public void eliminar(String id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("plata002");
        ref.removeValue()
                .addOnSuccessListener(unsued -> Log.d("Firabase", "plataforma eliminada"))
                .addOnFailureListener(e -> Log.e("Firebase", "Error al eliminar"));
    }


}
