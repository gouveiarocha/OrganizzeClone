package com.example.organizzeclone.Modelo;

import com.example.organizzeclone.Helper.FirebaseUtils;
import com.example.organizzeclone.Helper.Base64Custom;
import com.google.firebase.database.Exclude;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private Double totReceita = 0.00;
    private Double totDespesa = 0.00;

    public Usuario() {
        this.id = Base64Custom.codificarBase64(email);
    }

    public Usuario(String nome) {
        this.id = Base64Custom.codificarBase64(email);
        this.nome = nome;
    }

    public Usuario(String email, String senha) {
        this.id = Base64Custom.codificarBase64(email);
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha) {
        this.id = Base64Custom.codificarBase64(email);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotReceita() {
        return totReceita;
    }

    public void setTotReceita(Double totReceita) {
        this.totReceita = totReceita;
    }

    public Double getTotDespesa() {
        return totDespesa;
    }

    public void setTotDespesa(Double totDespesa) {
        this.totDespesa = totDespesa;
    }

    public void salvar(){
        FirebaseUtils.refUsuarios().child(this.id).setValue(this);
    }

}
