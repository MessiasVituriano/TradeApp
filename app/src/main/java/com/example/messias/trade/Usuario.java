package com.example.messias.trade;

/**
 * Created by Messias on 06/12/2016.
 */

public class Usuario {
    String nome;
    String email;
    Double lat;
    Double lon;
    int distancia;


    public Usuario(){

    }

    public Usuario(String nome, String email, double lat, Double lon, int distancia){
        this.nome = nome;
        this.email = email;
        this.lat = lat;
        this.lon = lon;
        this.distancia = distancia;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLat(Double lat) { this.lat = lat; }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public int getDistancia() {
        return distancia;


    }
}
