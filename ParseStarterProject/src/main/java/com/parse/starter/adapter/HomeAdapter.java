package com.parse.starter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<ParseObject> postagens;
    public HomeAdapter(@NonNull Context context, @NonNull ArrayList<ParseObject> objects) {
        super(context, 0, objects);
        this.context = context;
        this.postagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view==null){

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService
                    (context.LAYOUT_INFLATER_SERVICE);

            view= layoutInflater.inflate(R.layout.lista_postagens,parent,false);
        }

        // verifica se tem postagens

        if (postagens.size()>0){

            ImageView imagemPostagem = (ImageView)view.findViewById(R.id.img_postagens);

            ParseObject parseObject = postagens.get(position);

            //parseObject.getParseFile("imagem");
            Picasso.with(context).load(parseObject.getParseFile("imagem").getUrl())
                    .into(imagemPostagem);

        }

        return view;
    }
}
