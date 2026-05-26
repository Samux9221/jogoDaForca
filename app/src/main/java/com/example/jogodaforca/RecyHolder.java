package com.example.jogodaforca;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyHolder extends RecyclerView.ViewHolder {

    private TextView txPalavra, txCategoria;
    public RecyHolder(@NonNull View itemView) {
        super(itemView);
        txPalavra = itemView.findViewById(R.id.id_palavra);
        txCategoria = itemView.findViewById(R.id.id_categoria);
    }
}
