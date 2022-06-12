package com.example.siger.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.siger.MainActivity;
import com.example.siger.MainActivity_Home;
import com.example.siger.PagoTarjeta;
import com.example.siger.R;
import com.example.siger.Registrar;
import com.example.siger.databinding.FragmentHomeBinding;
import com.example.siger.ui.gallery.GalleryFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }

    //Se separa la logica de los componenetes y la logica de la inicializacion para evitar algun fallo
    //en el momento de inicializar la aplicacion

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Button reserva = view.findViewById(R.id.btn_reserva);
        Button platos = view.findViewById(R.id.btn_platos);
        Button bebidas = view.findViewById(R.id.btn_bebidas);
        Button contacto = view.findViewById(R.id.btn_contacto);
        Button pagar = view.findViewById(R.id.btn_pagar);
        Button inicio = view.findViewById(R.id.nav_home);

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_gallery_to_nav_home);

            }
        });




        platos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_slideshow);

            }

        });
        reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_gallery2);

            }
        });

        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.pagoTarjeta);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}