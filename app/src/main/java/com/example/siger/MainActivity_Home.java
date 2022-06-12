package com.example.siger;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siger.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.siger.databinding.ActivityMainHomeBinding;

public class MainActivity_Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainHomeBinding binding;
    private TextView name2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.nav_header_main_activity_home);

     binding = ActivityMainHomeBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

       setSupportActionBar(binding.appBarMainActivityHome.toolbar);
        binding.appBarMainActivityHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Pasanndo el nombre del usuario al NavHeader
        Bundle bundle =  getIntent().getExtras();
        String datos = bundle.getString("name_user");

        View headerView = navigationView.getHeaderView(0);

        TextView navUsername = (TextView) headerView.findViewById(R.id.txt_name);
        navUsername.setText(datos);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity__home, menu);
        return true;
    }

    //Metodo para cerrar sesion
    public void onGroupItemClick(@NonNull MenuItem item) throws IllegalStateException {
        switch (item.getItemId()){
            case R.id.nav_cerrar:
                dialog();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());

        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_activity_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void dialog(){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("¿Seguro que desea cerrar la sesion?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Cerrando sesion...", Toast.LENGTH_SHORT).show();
                Intent inte=new Intent(MainActivity_Home.this, MainActivity.class);
                startActivity(inte);
                finish();

            }
        });
        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Gracias por seguir en SIGER", Toast.LENGTH_SHORT).show();
            }
        }).show();

    }






}