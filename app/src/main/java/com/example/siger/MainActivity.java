package com.example.siger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siger.BBDD.BDusuario;
import com.example.siger.Metodos.Usuario;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
EditText usuario, contrasena;
Button btn_iniciar_sesion, btnRegistrar;
BDusuario bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.Edit_usuario);
        contrasena = (EditText) findViewById(R.id.Edit_pass);
        btn_iniciar_sesion = (Button) findViewById(R.id.Btn_iniSesion);
        btnRegistrar = (Button) findViewById(R.id.btn_registro);

        btn_iniciar_sesion.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        bd=new BDusuario(this);

    }

    @Override
    public void onClick(View view) {
        //Validacion de iniciar sesion
        switch (view.getId()){
            case R.id.Btn_iniSesion:
                String user =usuario.getText().toString();
                String passw = contrasena.getText().toString();
                if (usuario.equals("")&&passw.equals("")){
                    Toast.makeText(this, "Error: campos vacios", Toast.LENGTH_SHORT).show();
                    //Si es igual a 1 significa que si coincide o que lo encontro en la base de datos.
                }else if (bd.login(user,passw)==1){
                    Usuario us2=bd.getUsuario(user,passw);
                    Toast.makeText(this, "Datos validados correctamente", Toast.LENGTH_SHORT).show();

                    String n = usuario.getText().toString();


                    Intent in2=new Intent(MainActivity.this, MainActivity_Home.class);
                    Bundle info =new Bundle();
                    info.putString("name_user",n);
                    in2.putExtras(info);
                    startActivity(in2);
                    finish();
                }else{
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
                break;
//Intent para volver a la pantalla de inicio si presiona cancelar
            case R.id.btn_registro:
                Intent in=new Intent(MainActivity.this, Registrar.class);
                startActivity(in);
                break;
        }
    }


}