package com.android.proyectandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;


public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.ViewHolder> {
    private List<InformacionTarea> listado;


 //Referencia de cada uno de los cards que hay en pantalla
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreTarea;
        public TextView fechaTarea;
        public ViewHolder(View v) {
            super(v);
            // establecer los valores de cada uno de los cards
            nombreTarea=(TextView) v.findViewById(R.id.nombreTarea);
            fechaTarea=(TextView) v.findViewById(R.id.fechaTarea);
        }
    }

    //Constructor que recibe el listado de todos los items que hay en la base de datos
    public TareasAdapter(List<InformacionTarea> listado) {
        this.listado=listado;
    }

   //crea un nuevo card
    @Override
    public TareasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
       //Crea card view por cada item de la lista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cards, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

//metodo que me permite recorrer el listado de items de la base de datos y setea los valores
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.nombreTarea.setText(listado.get(position).getNombreTarea());
        Date d = listado.get(position).getFechaTarea();
        if(d==null){
            d= new Date(System.currentTimeMillis());
            System.out.println("VACIO!!!!");
        }else{

        }
        holder.fechaTarea.setText(d.toString());

    }

    // Retorna el tamaño de la lista de datos
    @Override
    public int getItemCount() {
        return listado.size();
    }
}
