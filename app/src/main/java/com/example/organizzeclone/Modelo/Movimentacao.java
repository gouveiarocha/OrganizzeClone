package com.example.organizzeclone.Modelo;

import com.example.organizzeclone.Helper.FirebaseUtils;
import com.example.organizzeclone.Helper.DateCustom;

public class Movimentacao {

    private double valor;
    private String key, data, categoria, descricao, tipo;

    public Movimentacao() {
    }

    public Movimentacao(double valor, String data, String categoria, String descricao, String tipo) {
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void salvar(String data) {

        String idUsuario = FirebaseUtils.getIdUsuario();       //Recuperar Email/ID
        String mesAno = DateCustom.getDataMesAno(data);         //Recuperar MesAno.

        //Salvar
        FirebaseUtils.refMovimentacoes()
                .child(idUsuario)   //id do usuario
                .child(mesAno)      //mes e ano
                .push()             //id da movimentação
                .setValue(this);    //grava os dados

    }

}
