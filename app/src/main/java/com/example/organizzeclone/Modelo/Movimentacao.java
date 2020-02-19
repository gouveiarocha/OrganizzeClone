package com.example.organizzeclone.Modelo;

import com.example.organizzeclone.Config.ConfigFirebase;
import com.example.organizzeclone.Helper.DateCustom;

public class Movimentacao {

    private double valor;
    private String data, categoria, descricao, tipo;

    public Movimentacao() {
    }

    public Movimentacao(double valor, String data, String categoria, String descricao, String tipo) {
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        this.descricao = descricao;
        this.tipo = tipo;
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

        String idUsuario = ConfigFirebase.getIdUsuario();       //Recuperar Email/ID
        String mesAno = DateCustom.getDataMesAno(data);         //Recuperar MesAno.

        //Salvar
        ConfigFirebase.refMovimentacoes()
                .child(idUsuario)   //id do usuario
                .child(mesAno)      //mes e ano
                .push()             //id da movimentação
                .setValue(this);    //grava os dados

    }

}
