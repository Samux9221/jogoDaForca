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

    public String getDica(){
        return dica;
    }

    public void setDica(String dica){
        this.dica = dica;
    }

    public int getNivel(){
        return nivel;
    }

    public void setNivel(int nivel){
        this.nivel = nivel;
    }

    // Alterado para String, pois o objetivo é retornar texto!
    public String nivelEmTexto() {

        // Como o metodo está dentro da classe Palavra, usamos o getNivel() direto dela
        if (getNivel() == 1) {
            return "Fácil";
        }
        else if (getNivel() == 2) {
            return "Médio";
        }
        else if (getNivel() == 3) {
            return "Difícil";
        }

        return "Não definido";
    }

}
