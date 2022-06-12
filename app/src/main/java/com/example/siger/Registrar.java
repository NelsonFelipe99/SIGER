package com.example.siger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siger.BBDD.BDusuario;
import com.example.siger.Metodos.Usuario;

import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity implements View.OnClickListener {
    private EditText nombre,correo,contrasena;
    private Button Enviar, Cancelar;
    private BDusuario bdus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        nombre = (EditText) findViewById(R.id.Edit_NombreRegistro);
        correo = (EditText) findViewById(R.id.Edit_correo);
        contrasena = (EditText) findViewById(R.id.Edit_contrasena);
        Enviar = (Button) findViewById(R.id.btn_enviarRegistro);
        Cancelar = (Button) findViewById(R.id.btn_CancelarRegistro);

        Enviar.setOnClickListener(this);
        Cancelar.setOnClickListener(this);
        bdus=new BDusuario(this);

    }
//Metodo para registrar
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_enviarRegistro:
                Usuario u =new Usuario();
                u.setNombre(nombre.getText().toString());
                u.setCorreo(correo.getText().toString());
                u.setContrasena(contrasena.getText().toString());
                if(!u.isNull()){
                    Toast.makeText(this, "Error: los campos estan vacios", Toast.LENGTH_SHORT).show();
                }
                else if (bdus.inserUser(u)){
                    Toast.makeText(this,"Registro realizado con exito", Toast.LENGTH_SHORT).show();
                    Intent inte=new Intent(Registrar.this, MainActivity.class);
                    startActivity(inte);
                    finish();
            }else{
                    Toast.makeText(this,"Usuario ya registrado", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_CancelarRegistro:
                Intent in=new Intent(Registrar.this, MainActivity.class);
                startActivity(in);
                finish();
                break;
        }
    }



}