package com.example.jogodaforca;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TelaJogo extends AppCompatActivity implements View.OnClickListener {

    private ImageView imagem;
    private ArrayList<Integer> listaImagens;
    private int indiceListaImagens;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_jogo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //ligando imagem de forca do primeiro layout e da segunda tela
        imagem = findViewById(R.id.imageView);
        b1 = findViewById(R.id.id1);

        indiceListaImagens = 0;
        listaImagens = new ArrayList<Integer>(); //instanciando o arrayList, é necessário

        //alimentando as imagens na lista de imagens, em ordem logica e sequancial
        listaImagens.add(R.drawable.forca_1_9);
        listaImagens.add(R.drawable.forca_2_9);
        listaImagens.add(R.drawable.forca_3_9);
        listaImagens.add(R.drawable.forca_4_9);
        listaImagens.add(R.drawable.forca_5_9);
        listaImagens.add(R.drawable.forca_6_9);
        listaImagens.add(R.drawable.forca_7_9);
        listaImagens.add(R.drawable.forca_8_9);
        listaImagens.add(R.drawable.forca_9_9);
        listaImagens.add(R.drawable.forca_10_9);

        b1.setOnClickListener(this);
    }

    public void atualizaForca(){
        imagem.setImageResource(listaImagens.get(indiceListaImagens));

        indiceListaImagens++;
    }

    @Override
    public void onClick(View v) {
        atualizaForca();
    }
}