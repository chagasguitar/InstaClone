package com.parse.starter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;

/**
 * Created by Quetzal on 14/07/2017.
 */

public class UsuariosAdapter extends ArrayAdapter{

    private Context context;
    ArrayList<ParseUser> usuarios;


    public UsuariosAdapter(@NonNull Context context, @NonNull ArrayList<ParseUser> usuarios) {
        super(context, 0, usuarios);

        this.context = context;
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view==null){

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService
                    (context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.lista_usuarios,parent,false);

        }

        TextView username = (TextView)view.findViewById(R.id.text_usuario);

        ParseUser parseUser = usuarios.get(position);
        username.setText(parseUser.getUsername());

        return view;

    }
}
