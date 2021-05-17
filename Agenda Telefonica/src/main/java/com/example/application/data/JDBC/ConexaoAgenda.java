package com.example.application.data.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class ConexaoAgenda {
    public Connection getConnection(){
        try{
            String nomeUsuario = "postgres";
            String senhaUsuario = "123456";
            String enderecoServidor = "localhost";
            String nomeBanco = "agenda_db";
            //Driver.
            Connection conn = DriverManager.getConnection("jdbc:postgresql://"+enderecoServidor+"/"+nomeBanco, nomeUsuario, senhaUsuario);
            System.out.println("Conectado com sucesso");
            return conn;

        }catch (SQLException ex){
            System.out.println("Erro, não abri conexão");
            throw new RuntimeException(ex);
        }
    }
}
