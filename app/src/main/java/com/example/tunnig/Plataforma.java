package com.example.tunnig;

public class Plataforma {
    private String uid;
    private String year;
    private String fabricante;
    private String modelo;
    private String edicion;


    public Plataforma() {
    }/// / == BOB == ///

    public Plataforma(String uid, String year, String fabricante, String modelo, String edicion) {
        this.uid = uid;
        this.year = year;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.edicion = edicion;
    }

    public Plataforma(String uid, String modelo) {
        this.uid = uid;
        this.modelo = modelo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }
}