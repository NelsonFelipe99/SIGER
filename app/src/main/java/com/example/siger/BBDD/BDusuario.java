package com.example.siger.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siger.Metodos.Usuario;

import java.util.ArrayList;

public class BDusuario {
    Context c;
    Usuario user;
    ArrayList<Usuario> Lista;

    SQLiteDatabase siger;
    String bd="siger";
    String table ="create table if not exists users(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre text, correo text, contrasena text)";

    public BDusuario(Context c){
            this.c=c;
            //Comando para crear o abrir la base de datos.
         siger=c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
         siger.execSQL(table);
         user=new Usuario();
    }

    public boolean inserUser(Usuario user){
        if(buscar(user.getNombre())==0){
            ContentValues cv = new ContentValues();
            cv.put("nombre",user.getNombre());
            cv.put("correo",user.getCorreo());
            cv.put("contrasena",user.getContrasena());
            return (siger.insert("users",null,cv)>0);

        }else{
            return false;
        }
    }
//Contador de la cantidad de usuarios
    public int buscar (String user){
        int contador=0;
        Lista = selectUsuarios();
        for(Usuario userss:Lista){
            //Aqui verifico que el usuario que recibo no exista
            if (userss.getCorreo().equals(user)){
                contador++;
            }
        }
        return contador;

    }

//Seleccionar todos los usuarios
    public ArrayList<Usuario> selectUsuarios(){
        ArrayList<Usuario> Lista= new ArrayList<Usuario>();
        Lista.clear();
        //Cursor para ejecutar la query
        Cursor cr = siger.rawQuery("select * from users",null);
        if (cr!=null&&cr.moveToFirst()){
            do {
                Usuario us=new Usuario();
                us.setId(cr.getInt(0));
                us.setNombre(cr.getString(1));
                us.setCorreo(cr.getString(2));
                us.setContrasena(cr.getString(3));

                Lista.add(us);
            }while (cr.moveToNext());

        }
        return Lista;
    }

    //Funcion de login
    public int login(String user, String pass) {
        int sum = 0;
        Cursor cr = siger.rawQuery("select * from users", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                if (cr.getString(1).equals(user) && cr.getString(2).equals(pass)) {
                    sum++;
                }

                }while (cr.moveToNext());

            }
        return sum;
        }
    //Funcion para obtener el usuario
        public Usuario getUsuario(String use, String password){
            Lista=selectUsuarios();
            for (Usuario us2:Lista){
                if(us2.getNombre().equals(use)&&us2.getContrasena().equals(password)){
                    return us2;
                }
            }
            return null;
        }
    }
