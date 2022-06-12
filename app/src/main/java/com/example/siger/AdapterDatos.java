package com.example.siger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> implements View.OnClickListener {



    ArrayList<Platos>ListaPlatos;
    private View.OnClickListener listener;

    public AdapterDatos(ArrayList<Platos> listaPlatos) {
        ListaPlatos = listaPlatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        //Escucha de los eventos
        view.setOnClickListener(this);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {

        holder.txtNombre.setText(ListaPlatos.get(position).getNombre());
        holder.txtInfo.setText(ListaPlatos.get(position).getInfo());
        holder.foto.setImageResource(ListaPlatos.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return ListaPlatos.size();
    }

    public void setOnclick(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }

    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView txtNombre, txtInfo;
        ImageView foto;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txtNombre = (TextView)itemView.findViewById(R.id.txtplatos);
            txtInfo = (TextView)itemView.findViewById(R.id.txtinfo);
            foto = (ImageView) itemView.findViewById(R.id.img_plato);
        }
    }
}
