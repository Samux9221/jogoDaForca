package com.example.jogodaforca;

public class Palavra {
    private String palavraDigitada, categoria, dica;
    private int nivel;

    public String getPalavraDigitada() {
        return palavraDigitada;
    }

    public void setPalavraDigitada(String palavraDigitada) {
        this.palavraDigitada = palavraDigitada;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String dica(){
        return dica;
    }

    public void setDica(String dica){
        this.dica = dica;
    }

    public int nivel(){
        return nivel;
    }

    public void setNivel(int nivel){
        this.nivel = nivel;
    }

}
