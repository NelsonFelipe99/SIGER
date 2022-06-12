package com.example.siger.ui.gallery;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.siger.BBDD.BDreserva;
import com.example.siger.MainActivity_Home;
import com.example.siger.Metodos.Reserva;
import com.example.siger.R;
import com.example.siger.databinding.FragmentGalleryBinding;
import com.example.siger.ui.home.HomeFragment;
import com.example.siger.ui.home.HomeViewModel;

import java.util.Calendar;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private TextView fecha,hora, comentario;
    private Button btn_fecha, btn_hora,btn_enviarR, btn_editReserva;
    private int dia, mes, year, time, minutos;
    private BDreserva bdR;



    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
          galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

    binding = FragmentGalleryBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        fecha =view.findViewById(R.id.txt_fecha);
        hora = view.findViewById(R.id.txt_hora);
        comentario = view.findViewById(R.id.edit_comentario);

        btn_fecha = view.findViewById(R.id.btn_fecha);
        btn_hora = view.findViewById(R.id.btn_hora);
        btn_enviarR = view.findViewById(R.id.btn_enviarR);
        btn_editReserva = view.findViewById(R.id.btn_editReservs);


        btn_fecha.setOnClickListener(this);
        btn_hora.setOnClickListener(this);
        btn_enviarR.setOnClickListener(this);
        btn_editReserva.setOnClickListener(this);
        bdR =new BDreserva(getContext());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//Evento para el calendario
    @Override
    public void onClick(View view) {
        if (view==btn_fecha){
            final Calendar calen = Calendar.getInstance();
            dia=calen.get(Calendar.DAY_OF_MONTH);
            mes = calen.get(Calendar.MONTH);
            year = calen.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int ano, int mesAno, int day) {

                    if (ano>=year&&mesAno>=mes&&day>=dia){
                        fecha.setText((day)+"/"+(mesAno)+"/"+(ano));

                    }else{
                        Toast.makeText(getContext(), "Fecha no disponible", Toast.LENGTH_SHORT).show();
                    }

                }
            },year,mes,dia);
            datePickerDialog.show();

        }
        //Evento para la hora
        if(view==btn_hora){
            final Calendar horaC = Calendar.getInstance();
            time = horaC.get(Calendar.HOUR_OF_DAY);
            minutos= horaC.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog =new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int horas, int minute) {


                    hora.setText(horas+":"+minute);

                }
            },time,minutos,false);
            timePickerDialog.show();

        }
        if (view==btn_editReserva){
            Navigation.findNavController(view).navigate(R.id.action_nav_gallery_to_editar_reserva);
        }
//Aqui estoy enviando la informacion de la reserva.
     switch (view.getId()){
         case R.id.btn_enviarR:
             Reserva r= new Reserva();
             r.setFecha(fecha.getText().toString());
             r.setHora(hora.getText().toString());
             r.setComentario(comentario.getText().toString());

             if(!r.isNull()){
                 Toast.makeText(getContext(), "Error: los campos estan vacios", Toast.LENGTH_SHORT).show();

             }else if(bdR.insertReserve(r)){
                 Toast.makeText(getContext(),"Reserva realizada con exito", Toast.LENGTH_SHORT).show();
                 Navigation.findNavController(view).navigate(R.id.nav_home);

             }
     }


    }

}