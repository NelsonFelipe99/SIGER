package com.example.siger.Metodos;

public class Reserva {
    int id;
    String Fecha,Hora,Comentario;


    public Reserva(){

    }

    //Metodo constructor
    public Reserva(String fecha, String hora, String comentario) {
     Fecha = fecha;
     Hora = hora;
     Comentario = comentario;
    }

    //Funcion para validar que los campos no esten vacios
    public boolean isNull() {
        if (Fecha.equals("") && Hora.equals("") && Comentario.equals("")) {
            return false;

        } else {
            return true;
        }
    }


    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fecha='" + Fecha + '\'' +
                ", hora='" + Hora + '\'' +
                ", comentario='" + Comentario + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        this.Hora = hora;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        this.Comentario = comentario;
    }
}
