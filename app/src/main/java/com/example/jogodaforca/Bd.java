package com.example.jogodaforca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Bd extends SQLiteOpenHelper {

    // CORREÇÃO: Versão alterada para 2 para forçar o Android a reconstruir a tabela com as novas colunas
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "banco.db";

    // Construtor que recebe o contexto da Activity
    public Bd(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação da tabela oficial com TODAS as colunas necessárias
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS tabelaPalavra (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "palavra TEXT," +
                        "categoria TEXT," +
                        "dica TEXT," +
                        "nivel INTEGER)"
        );
    }

    public void salvarPalavra(Palavra p){
        SQLiteDatabase db = getWritableDatabase(); // Permissão para escrita
        ContentValues valores = new ContentValues();

        valores.put("palavra", p.getPalavraDigitada());
        valores.put("categoria", p.getCategoria());
        valores.put("dica", p.getDica());     // Salva a dica de verdade
        valores.put("nivel", p.getNivel());   // Salva o nível de verdade

        db.insert("tabelaPalavra", null, valores);
        db.close();
    }

    public ArrayList<Palavra> listarPalavras(){
        ArrayList<Palavra> lista = new ArrayList<Palavra>();
        SQLiteDatabase db = getReadableDatabase(); // Permissão para leitura
        Cursor cursor = db.query("tabelaPalavra", null, null, null, null, null, null);

        // Enquanto houver registros no banco de dados...
        while(cursor.moveToNext()){
            String palavra = cursor.getString(cursor.getColumnIndexOrThrow("palavra"));
            String categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"));
            String dica = cursor.getString(cursor.getColumnIndexOrThrow("dica"));
            int nivel = cursor.getInt(cursor.getColumnIndexOrThrow("nivel"));

            // Montando o objeto Palavra com as informações vindas do banco
            Palavra p = new Palavra();
            p.setPalavraDigitada(palavra);
            p.setCategoria(categoria);
            p.setDica(dica);
            p.setNivel(nivel);

            lista.add(p);
        }

        cursor.close();
        db.close();
        return lista;
    }

    // Metodo para apagar todo o banco de dados (Botao Excluir da Tela de Cadastro)
    public void limparTodasAsPalavras() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tabelaPalavra", null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tabelaPalavra");
        onCreate(db);
    }
}