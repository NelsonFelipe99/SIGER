package com.example.siger.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siger.AdapterDatos;
import com.example.siger.Platos;
import com.example.siger.PopPup.Risoto;
import com.example.siger.R;
import com.example.siger.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    private ArrayList <Platos>listaPlatos;
    RecyclerView recyclerplatos;
    private ImageView imgPopup;
    private TextView risoto;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

    binding = FragmentSlideshowBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaPlatos = new ArrayList<>();
        recyclerplatos = (RecyclerView)  view.findViewById(R.id.recycler_platos);
        recyclerplatos.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarPlatos();
        AdapterDatos adaptaderPlatos =new AdapterDatos(listaPlatos);
        //implementar onclikc
        adaptaderPlatos.setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), Risoto.class);
                startActivity(intent);

            }
        });
        recyclerplatos.setAdapter(adaptaderPlatos);

    }

    private void llenarPlatos() {

        listaPlatos.add(new Platos("Risoto", R.drawable.photo,"Risoto con tinta de calamar"));
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}