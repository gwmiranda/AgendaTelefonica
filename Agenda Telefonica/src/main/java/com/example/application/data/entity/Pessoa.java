package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Pessoa extends AbstractEntity {

    private String nome;
    private String sobrenome;
    private LocalDate data_nascimento;
    private String contato;
    private String contato_2;
    private String contato_3;
    private String parentesco;

    public Pessoa() {
    }

    public Pessoa(String nome, String sobrenome, LocalDate data_nascimento, String contato, String contato_2,
                  String contato_3, String parentesco) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.data_nascimento = data_nascimento;
        this.contato = contato;
        this.contato_2 = contato_2;
        this.contato_3 = contato_3;
        this.parentesco = parentesco;
    }



    public String getNome() {
        return nome;
    }

    public void  setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getContato_2() {
        return contato_2;
    }

    public void setContato_2(String contato_2) {
        this.contato_2 = contato_2;
    }

    public String getContato_3() {
        return contato_3;
    }

    public void setContato_3(String contato_3) {
        this.contato_3 = contato_3;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public void mostraPessoa(){
        System.out.println("ID : " +getId());
        System.out.println("Nome : " +getNome());
        System.out.println("Sobrenome : " +getSobrenome());
        System.out.println("Nascimento : " +getData_nascimento());
        System.out.println("Parentesco : " +getParentesco());
    }

}
