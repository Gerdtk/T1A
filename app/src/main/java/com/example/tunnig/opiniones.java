package com.example.tunnig;

public class opiniones {
    private String id;        // key push()
    private String uid;       // usuario
    private String plataforma; // opcional: modelo/uid plataforma
    private String opcion;    // "Bueno" | "Regular" | "Malo"
    private long timestamp;

    public opiniones() {}

    public opiniones(String id, String uid, String plataforma, String opcion, long timestamp) {
        this.id = id;
        this.uid = uid;
        this.plataforma = plataforma;
        this.opcion = opcion;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
