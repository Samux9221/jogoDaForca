package com.example.jogodaforca;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TelaJogo extends AppCompatActivity implements View.OnClickListener {

    private ImageView imagem;
    private ArrayList<Integer> listaImagens, listaIdsButtons;
    private ArrayList<String> listaPalavras;
    private int indiceListaImagens;
    private TextView texto;
    private String palavra;
    private char[] estado;

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

        indiceListaImagens = 0;
        listaImagens = new ArrayList<Integer>(); //instanciando o arrayList, é necessário
        listaPalavras = new ArrayList<String>();

        //alimentando palavras para o ArrayList
        listaPalavras.add("BANANA");
        listaPalavras.add("BOLA");
        listaPalavras.add("CASA");
        listaPalavras.add("IGREJA");
        listaPalavras.add("ESCOLA");
        listaPalavras.add("INSTITUTO");
        listaPalavras.add("CIDADE");
        listaPalavras.add("ESTADO");
        listaPalavras.add("SENTIMENTO");
        listaPalavras.add("ASFALTO");

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

        texto = findViewById(R.id.textView3);

        //inicializando a lista de Ids de button e adicionando na lista a referencia de numero inteiro da classe R de cada um dos buttons
        listaIdsButtons = new ArrayList<Integer>();
        listaIdsButtons.add(R.id.id1);
        listaIdsButtons.add(R.id.id2);
        listaIdsButtons.add(R.id.id3);
        listaIdsButtons.add(R.id.id4);
        listaIdsButtons.add(R.id.id5);
        listaIdsButtons.add(R.id.id6);
        listaIdsButtons.add(R.id.id7);
        listaIdsButtons.add(R.id.id8);
        listaIdsButtons.add(R.id.id9);
        listaIdsButtons.add(R.id.id10);
        listaIdsButtons.add(R.id.id11);
        listaIdsButtons.add(R.id.id12);
        listaIdsButtons.add(R.id.id13);
        listaIdsButtons.add(R.id.id14);
        listaIdsButtons.add(R.id.id15);
        listaIdsButtons.add(R.id.id16);
        listaIdsButtons.add(R.id.id17);
        listaIdsButtons.add(R.id.id18);
        listaIdsButtons.add(R.id.id19);
        listaIdsButtons.add(R.id.id20);
        listaIdsButtons.add(R.id.id21);
        listaIdsButtons.add(R.id.id22);
        listaIdsButtons.add(R.id.id23);
        listaIdsButtons.add(R.id.id24);
        listaIdsButtons.add(R.id.id25);
        listaIdsButtons.add(R.id.id26);

        //percorrendo toda a lista de Ids e tornando eles sensiveis ao toque
        for(int j = 0; j < listaIdsButtons.size(); j++){
            Button b = findViewById(listaIdsButtons.get(j));
            b.setOnClickListener(this);
        }
        iniciarJogo();
    }

    //metodo de sorteioDasPalavras
    public String sorteiaPalavra(){

        //embaralhando a lista, não sabemos mais a ordem
        Collections.shuffle(listaPalavras);

        return listaPalavras.get(0);
    }

    //metodo de iniciar jogo
    public void iniciarJogo(){
        imagem.setImageResource(R.drawable.forca_0_9); //voltando a imagem para oadrão inicial
        palavra = sorteiaPalavra();

        estado = new char[palavra.length()]; //inicializando o vetor com a quantidade de caracteres da palavra sorteada

        for(int i = 0; i < palavra.length(); i++){
            estado[i] = '_';
        }

        //ajustando para o texto com os "_" ficarem bem espaçadas
        atualizaTexto();
    }

    public void atualizaTexto(){
        String temporaria = new String();
        temporaria = "";

        for (int i = 0; i < estado.length; i++){
           temporaria += estado[i] + " "; //estado tem somente os "_", vamos percorrer e ir adicionar espaço
        }

        texto.setText(temporaria);
    }

    public void atualizaForca(){
        imagem.setImageResource(listaImagens.get(indiceListaImagens));

        indiceListaImagens++;
    }

    public void verificaLetra(char c){
        boolean status = false;

        for(int i = 0; i < palavra.length(); i++){
            if(palavra.charAt(i)==c){
                status = true;
                estado[i] = c;
            }
        }

        //se errar enforco
        if(!status){
            atualizaForca();
        }

        //se acertar atualizo
        else{
            atualizaTexto();
        }
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v; //aqui entre parentese (Button) estamos faznedo um casting, forçando e dizendo que o View que é passado para o metodo onClick sempre será um button

        //tenho que passar como parametro char
        verificaLetra(b.getText().toString().charAt(0));
        b.setEnabled(false);
    }
}