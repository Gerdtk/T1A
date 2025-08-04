package com.example.tunnig;

public class Res {
    private String uid;
    private String nombre;
    private String correo;
    private String contra;

    public Res(){
        
    }//// == BOB == ///
    ///
    public Res(String uid, String nombre, String correo, String contra){
        this.uid = uid;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
    }

    public Res(String uid, String nombre) {
    }

    public String getUid() {return uid;}
    public void setUid(String uid) {this.uid = uid;}
    
    
    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
}
