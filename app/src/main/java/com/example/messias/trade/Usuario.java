package com.example.messias.trade;

/**
 * Created by Messias on 06/12/2016.
 */

public class Usuario {
    String nome;
    String email;
    String lat;
    String lon;
    String distancia;


    public Usuario(){

    }

    public Usuario(String nome, String email, String lat, String lon, String distancia){
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

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getDistancia() {
        return distancia;
    }
}
