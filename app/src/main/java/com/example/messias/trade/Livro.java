package com.example.messias.trade;

/**
 * Created by Messias on 16/11/2016.
 */

public class Livro {
    private String nome;
    private String descricao;
    private String estado;
    private String userID;

    private Livro(){

    }

    public Livro(String nome, String descricao, String estado, String userID){

        this.nome = nome;
        this.descricao = descricao;
        this.estado = estado;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\nDescrição: " +
                descricao + "\nEstado: " + estado + "\nUser ID" + userID;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEstado() {
        return estado;
    }

    public String getNome() {
        return nome;
    }

    public String getUserID() {
        return userID;
    }
}
