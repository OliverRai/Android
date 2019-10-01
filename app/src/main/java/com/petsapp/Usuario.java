package com.petsapp;

import com.google.firebase.database.Exclude;

public class Usuario {
    private String nome, email, definicao, mKey;

    public Usuario() {
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

    public String getDefinicao() {
        return definicao;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}

