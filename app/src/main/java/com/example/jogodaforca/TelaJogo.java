package com.example.jogodaforca;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<Palavra> listaPalavras, listaFacil, listaMedia, listaDificil, listaPalavraLocal;
    private int indiceListaImagens, contaErro, contaAcerto, vitoria, nivel, pontuacaoTotal;
    private TextView texto, textAcertos, textErros, textNivel, textCategoria, textPontuacao;
    private String palavra;
    private Button btnDica;
    private char[] estado;
    private static final String PREF_NAME = "JogoPrefs";
    private static final String KEY_VITORIAS = "total_vitorias";
    private static final String KEY_PONTUACAO = "total_pontos";
    private Bd bancoDeDados;
    private Palavra palavraAtualObj;

    @SuppressLint("MissingInflatedId")
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

        /* --- --- */

        listaPalavras = new ArrayList<Palavra>();
        listaFacil = new ArrayList<Palavra>();
        listaMedia = new ArrayList<Palavra>();
        listaDificil = new ArrayList<Palavra>();

        Bd bancoDeDados = new Bd(this);

        listaPalavras = bancoDeDados.listarPalavras();

        /* ---  --- */

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

        contaErro = 0;
        contaAcerto = 0;

        btnDica = findViewById(R.id.btnDica);

        texto = findViewById(R.id.textView3);

        textAcertos = findViewById(R.id.textAcertos);
        textErros = findViewById(R.id.textErros);
        textNivel = findViewById(R.id.textView11);
        textCategoria = findViewById(R.id.textView10);
        textPontuacao = findViewById(R.id.textView9);

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
            b.setEnabled(true);

        }

        /* Aqui vamos percorrer toda a listaPalavra para dividir em 3 listas, de acordo com o nivel de cada uma delas */
        int b = 0;
        while(b < listaPalavras.size()){

            Palavra palavraAtual = listaPalavras.get(b);

            if(palavraAtual.getNivel() == 1){
                listaFacil.add(palavraAtual);
            }
            else if(palavraAtual.getNivel() == 2){
                listaMedia.add(palavraAtual);
            }
            else if(palavraAtual.getNivel() == 3){
                listaDificil.add(palavraAtual);
            }
            b++;
        }

        carregarVitoria();
        iniciarJogo(verificaNivel());
    }

    //metodo de sorteioDasPalavras
    public String sorteiaPalavra(){

        //embaralhando a lista, não sabemos mais a ordem
        Collections.shuffle(listaPalavraLocal);

        return listaPalavraLocal.get(0).getPalavraDigitada().toUpperCase(); //garantindo que a palavra sorteada esteja com letra maiúscula
    }

    //metodo de iniciar jogo
    public void iniciarJogo(int nivel){

        listaPalavraLocal = new ArrayList<Palavra>();


        if(nivel == 1){
            listaPalavraLocal = listaFacil;
        }
        else if(nivel == 2){
            listaPalavraLocal = listaMedia;
        }
        else if(nivel == 3){
            listaPalavraLocal = listaDificil;
        }

        //percorrendo toda a lista de Ids e tornando eles sensiveis ao toque
        for(int j = 0; j < listaIdsButtons.size(); j++){
            Button b = findViewById(listaIdsButtons.get(j));
            b.setEnabled(true);
        }

        // 1. CORREÇÃO: Se estiver vazio, mostra o alerta e PARA o código aqui com o 'return'
        if (listaPalavraLocal.isEmpty()) {
            AlertDialog.Builder caixa = new AlertDialog.Builder(this);
            caixa.setTitle("Nenhuma palavra sua!");
            caixa.setMessage("Vamos usar algumas palavras padrão para você brincar agora.\n\nPara personalizar o jogo, vá em: Tela Inicial ➔ Configurações ➔ Cadastrar Palavra.");

            caixa.setPositiveButton("Jogar com Padrão", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Palavra p1 = new Palavra();
                    p1.setPalavraDigitada("BANANA");

                    Palavra p2 = new Palavra();
                    p2.setPalavraDigitada("COMPUTADOR");

                    listaPalavraLocal.add(p1);
                    listaPalavraLocal.add(p2);

                    // Agora que a lista foi preenchida, chama o iniciarJogo de verdade
                    iniciarJogo(1);
                }
            });

            caixa.setCancelable(false);
            caixa.show();
            return; // IMPORTANTE: Impede o código de continuar para as linhas de baixo antes da hora!
        }
        indiceListaImagens = 0;

        imagem.setImageResource(R.drawable.forca_0_9); //voltando a imagem para o padrão inicial
        palavra = sorteiaPalavra();

        contaErro = 0;
        contaAcerto = 0;

        btnDica.setVisibility(View.INVISIBLE);
        btnDica.setEnabled(true);

        //zerando as informações de jogadas no display
        textAcertos.setText(Integer.toString(contaAcerto));
        textErros.setText(Integer.toString(contaErro) + "/" + Integer.toString(listaImagens.size()));
        textNivel.setText("\uD83D\uDD25 Nivel: " + listaPalavras.get(0).nivelEmTexto());
        textPontuacao.setText("⭐ Pontos:  " + pontuacaoTotal);
        textCategoria.setText("\uD83D\uDCDA Categoria: " + listaPalavraLocal.get(0).getCategoria());

        estado = new char[palavra.length()]; //inicializando o vetor com a quantidade de caracteres da palavra sorteada

        for(int i = 0; i < palavra.length(); i++){
            estado[i] = '_';
        }

        //ajustando para o texto com os "_" ficarem bem espaçadas
        atualizaTexto();
    }

    //metodos referentes ao armazenamento permanente e localmente no celular
    private void salvarVitoria() {
        getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .edit()
                .putInt(KEY_VITORIAS, vitoria)
                .apply();
    }

    private void carregarVitoria() {
        vitoria = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getInt(KEY_VITORIAS, 0);
    }

    private void salvarPontos() {
        getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .edit()
                .putInt(KEY_PONTUACAO, pontuacaoTotal)
                .apply();
    }

    private void carregarPontos() {
        vitoria = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getInt(KEY_PONTUACAO, 0);
    }

    public void verificaSeTerminou(){
        boolean verifica = false;

        for(int i = 0; i < estado.length; i++){
            if(estado[i] == '_'){
                //se der true, é porque ainda tem jogo - falta uma jogada
                verifica = true;
            }
        }

        //se verifica estiver false, jogo ganho
        if(!verifica){

            // cada erro custa 1 imagem, se sobrou imagem, ele ganha bônus
            int tentativasSobrantes = (listaImagens.size() - contaErro);
            int pontosDaRodada = 100 + (tentativasSobrantes * 10);

            // Se ele usou dica (verifique uma flag se quiser)
            // pontosDaRodada -= 20;

            pontuacaoTotal += pontosDaRodada;
            salvarPontos();
            //mostrar mensagem de quantos pontos foram totalizados
            Toast.makeText(this, "Você ganhou " + pontosDaRodada + " pontos!", Toast.LENGTH_LONG).show();

            //instanciando a caixa com mensagem para o usuário
            AlertDialog.Builder caixa = new AlertDialog.Builder(this);

            //contando uma vitoria a mais
            vitoria++;
            salvarVitoria();

            caixa.setTitle("Você Venceeeeu!!!");
            caixa.setMessage("Deseja jogar novamente?");
            caixa.setPositiveButton("Jogar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    iniciarJogo(verificaNivel());
                }
            });
            caixa.show();
        }

        if(contaErro >= listaImagens.size()){
            //instanciando a caixa com mensagem para o usuário
            AlertDialog.Builder caixa = new AlertDialog.Builder(this);

            caixa.setTitle("Você perdeu, playboy!!!");
            caixa.setMessage("Deseja jogar novamente?");
            caixa.setPositiveButton("Jogar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    iniciarJogo(verificaNivel());
                }
            });
            caixa.show();
        }
    }

    public int verificaNivel(){

        if(vitoria >= 0 && vitoria <= 3){
            return 1; //nivel facil
        }
        else if(vitoria <= 6){
            return 2; //nivel medio
        }
        else if(vitoria > 6){
            return 3; //nivel dificil
        }

        return 0;
    }

    //esse metodo serve para formatar os "_" e deixá-los organizados
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
            contaErro++;
            textErros.setText(Integer.toString(contaErro) + "/" + Integer.toString(listaImagens.size()));
            if(contaErro >= 2){
                btnDica.setVisibility(View.VISIBLE);
                btnDica.setOnClickListener(this);
            }
        }

        //se acertar atualizo
        else{
            atualizaTexto();
            contaAcerto++;
            textAcertos.setText(Integer.toString(contaAcerto));
        }
        verificaSeTerminou();
    }

    @Override
    public void onClick(View v) {
        if(v == btnDica){
            //instanciando a caixa com mensagem para o usuário
            AlertDialog.Builder caixa = new AlertDialog.Builder(this);

            caixa.setTitle("Você recebeu uma dica!!!");
            caixa.setMessage("A dica para esse mistério é: " + listaPalavras.get(0).getDica());
            caixa.setNegativeButton("Voltar", null);
            caixa.show();
            Toast.makeText(this, "Dica dada, leso", Toast.LENGTH_SHORT).show();
            return;

        }

        Button b = (Button) v; //aqui entre parentese (Button) estamos faznedo um casting, forçando e dizendo que o View que é passado para o metodo onClick sempre será um button

        //toUpperCase() coloca em caixa alta a letra do teclado
        char letraApertada = b.getText().toString().toUpperCase().charAt(0);

        //tenho que passar como parametro char
        verificaLetra(letraApertada);
        b.setEnabled(false);

    }
}