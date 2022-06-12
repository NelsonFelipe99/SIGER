package com.example.siger;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.siger.BBDD.BDreserva;
import com.example.siger.Metodos.Reserva;
import com.example.siger.databinding.FragmentGalleryBinding;
import com.example.siger.ui.gallery.GalleryViewModel;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editar_reserva#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editar_reserva extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private TextView fecha,hora, comentario;
    private Button btn_fecha, btn_hora,btn_enviarR;
    private int dia, mes, year, time, minutos;
    private BDreserva bdR;




    public editar_reserva() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editar_reserva.
     */
    // TODO: Rename and change types and number of parameters
    public static editar_reserva newInstance(String param1, String param2) {
        editar_reserva fragment = new editar_reserva();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_reserva, container, false);
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


        btn_fecha.setOnClickListener(this);
        btn_hora.setOnClickListener(this);
        btn_enviarR.setOnClickListener(this);
        bdR =new BDreserva(getContext());

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
                        fecha.setText(day+"/"+(mesAno)+"/"+ano);

                    }else{
                        Toast.makeText(getContext(), "Fecha no disponible", Toast.LENGTH_SHORT).show();
                    }

                }
            }
                    ,year,mes,dia);
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
                    Toast.makeText(getContext(),"Sus cambios se han guardado con exito", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.nav_home);

                }
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}