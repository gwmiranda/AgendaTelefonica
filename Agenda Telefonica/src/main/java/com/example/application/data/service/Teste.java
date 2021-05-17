package com.example.application.data.service;

import com.example.application.data.DAO.PessoaDao;
import com.example.application.data.entity.Pessoa;

import java.time.LocalDate;
import java.util.*;

public class Teste {

    public static void main(String[] args) {

//        new ConexaoAgenda().getConnection();
        Pessoa p = new Pessoa();
        p.setId(74);
        p.setNome("Update");
        p.setSobrenome("Miranda");
        p.setData_nascimento(LocalDate.of(2002, 3, 11));
        p.setParentesco("Irmão");

        PessoaDao dao = new PessoaDao();

        if(dao.update(p)){
            System.out.println("Alterado");
        }else{
            System.out.println("Não Alterado");
        }

//        PessoaDao dao = new PessoaDao();
//        List<Pessoa> pessoas = dao.GetlList();
//
//        if (pessoas != null) {
//            for (Pessoa pessoa : pessoas) {
//                pessoa.mostraPessoa();
//
//                System.out.println("------------------------------");
//            }
//        } else {
//            System.out.println("Lista Nula");
//        }
    }
}
