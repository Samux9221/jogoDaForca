package com.example.jogodaforca;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<RecyHolder> {
    //essa classe adaptador vai ser o adaptador do RecycleView para com o layout, vai juntar tudo

    private ArrayList<Palavra> lista;
    @NonNull
    @Override
    public RecyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Toda vez que informações serão apresentandas de forma dinamica, chamamos essa classe para inflar o layout e prepará-lo
        //vai pegar nosso layout e inflar dentro do RecyHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);

        return new RecyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
