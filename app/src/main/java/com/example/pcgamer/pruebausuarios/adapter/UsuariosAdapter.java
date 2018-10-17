package com.example.pcgamer.pruebausuarios.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pcgamer.pruebausuarios.Entidades.Usuario;
import com.example.pcgamer.pruebausuarios.R;

import java.util.List;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuariosHolder>{

    List<Usuario> listaUsuarios;

    public UsuariosAdapter(List<Usuario> ListaUsuarios){
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public UsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarios_list, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new UsuariosHolder(vista);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder, int position) {
        holder.txtIDUsuarios.setText(listaUsuarios.get(position).getIDUsuario().toString());
        holder.txtDirec.setText(listaUsuarios.get(position).getDireccion());
        holder.txtImage.setText(listaUsuarios.get(position).getImagen());
        holder.txtCreate.setText(listaUsuarios.get(position).getFechaCreate());
        holder.txtUpdate.setText(listaUsuarios.get(position).getFechaUpdate());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class UsuariosHolder extends RecyclerView.ViewHolder{

        TextView txtIDUsuarios, txtDirec, txtImage, txtUpdate, txtCreate;

        public UsuariosHolder(View itemView) {
            super(itemView);

            txtIDUsuarios=(TextView) itemView.findViewById(R.id.txtIDUsuarios);
            txtDirec=(TextView) itemView.findViewById(R.id.txtDirec);
            txtImage=(TextView) itemView.findViewById(R.id.txtImage);


        }
    }
}
