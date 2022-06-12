package com.example.siger.Metodos;

public class Usuario {
    int id;
    String Nombre, Correo, Contrasena;

    public Usuario() {

    }

    //Metodo constructor
    public Usuario(String nombre, String correo, String contrasena) {
        Nombre = nombre;
        Correo = correo;
        Contrasena = contrasena;
    }

    //Funcion para validar que los campos no esten vacios
    public boolean isNull() {
        if (Nombre.equals("") && Correo.equals("") && Contrasena.equals("")) {
            return false;

        } else {
            return true;
        }
    }

    //Funcion toString()
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Contrasena='" + Contrasena + '\'' +
                '}';
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

}


