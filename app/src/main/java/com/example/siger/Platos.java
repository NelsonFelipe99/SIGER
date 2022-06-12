package com.example.siger;

public class Platos {
    private String Nombre;
    private int foto;
    private String info;

    public Platos(){

    }

    public Platos(String nombre, int foto, String info) {
        Nombre = nombre;
        this.foto = foto;
        this.info = info;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
