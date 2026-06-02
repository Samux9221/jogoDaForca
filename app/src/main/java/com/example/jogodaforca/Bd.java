package com.example.jogodaforca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Bd extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "banco.db";

    //esse contexto do parametro é o contexto da activy do frontend que estaremos passando para o COnstrutor
    public Bd(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS tabelaPalavra (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "palavra TEXT," +
                        "categoria TEXT," +
                        "dica TEXT," +
                        "nivel INTEGER)"

        );
    }

    //vamos receber como parametro a classe Palavra que criamos e pegar as informações dele para jogar ao banco de dados
    public void salvarPalavra(Palavra p){
        SQLiteDatabase db = getWritableDatabase(); //pegando permissão para manipular o banco de dados, nesse caso, escrever

        //"array" responsavel por organizar um determinado conteudo de acordo com a sua coluna no BD
        ContentValues valores = new ContentValues();

        //atribuindo os valores da minha classe para o DB
        valores.put("palavra", p.getPalavraDigitada());
        valores.put("categoria", p.getCategoria());

        //inserindo oficialmente
        db.insert("tabelaPalavra", null, valores);

        db.close();
    }

    public ArrayList<Palavra> listarPalavras(){
        ArrayList<Palavra> lista = new ArrayList<Palavra>();
        SQLiteDatabase db = getReadableDatabase(); //pegando permissão para manipular o banco de dados, nesse caso, para leitura
        Cursor cursor = db.query("tabelaPalavra", null, null, null, null, null, null);

        //enquanto houver uma próxima linha no Banco de Dados...
        while(cursor.moveToNext()){
            String palavra = cursor.getString(cursor.getColumnIndexOrThrow("palavra"));
            String categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"));

            Palavra p = new Palavra();
            p.setPalavraDigitada(palavra);
            p.setCategoria(categoria);
            lista.add(p);
        }

        cursor.close();
        db.close();
        return lista;
    }

    //esse metodo onUpgrade serve alterarmos o banco de dados (sua estrutura) depois de já estar criado, usaremos esse metodo
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}