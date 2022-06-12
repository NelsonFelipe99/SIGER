package com.example.siger.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.siger.Metodos.Reserva;

import java.util.ArrayList;

public class BDreserva {

    Context r;
    Reserva res;
    ArrayList<Reserva> ListaR;
    SQLiteDatabase siger;
    String bdR="siger";
    String tableR ="create table if not exists reservas(id INTEGER PRIMARY KEY AUTOINCREMENT,fecha text, hora text, comentario text)";

    public BDreserva(Context r){
        this.r=r;
        //Comando para crear o abrir la base de datos.
        siger=r.openOrCreateDatabase(bdR,r.MODE_PRIVATE,null);
        siger.execSQL(tableR);
        res=new Reserva();
    }

    public boolean insertReserve(Reserva reser){
            ContentValues cv = new ContentValues();
            cv.put("fecha",reser.getFecha());
            cv.put("hora",reser.getHora());
            cv.put("comentario",reser.getComentario());
            return (siger.insert("reservas",null,cv)>0);

    }
}
