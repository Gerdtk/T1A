package com.example.tunnig;

public class Plata002 {
    private String fabricante;
    private String modelo;
    private String año;
    private String ediciones;


    public Plata002() {

    }

    public Plata002(String fabricante, String modelo, String año, String ediciones) {
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.año = año;
        this.ediciones = ediciones;
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

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(String ediciones) {
        this.ediciones = ediciones;
    }

}
