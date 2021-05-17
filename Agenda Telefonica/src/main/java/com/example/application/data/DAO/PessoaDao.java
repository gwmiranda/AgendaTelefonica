package com.example.application.data.DAO;

import com.example.application.data.JDBC.ConexaoAgenda;
import com.example.application.data.entity.Pessoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.sql.SQLException;



public class PessoaDao {
    private Connection con;

    public PessoaDao(){
        this.con = new ConexaoAgenda().getConnection();
    }

    public boolean add(Pessoa p){
        String sql = "INSERT INTO tb_pessoa(nome, sobrenome, data_nascimento, parentesco, contato, contato1, contato2) VALUES (?,?,'"+p.getData_nascimento()+
                "',?,?,?,?);";

        try{
            PreparedStatement stmt = con.prepareStatement(sql);stmt.setString(
            1,p.getNome());
            stmt.setString(2,p.getSobrenome());
            stmt.setString(3,p.getParentesco());
            stmt.setString(4,p.getContato());
            stmt.setString(5,p.getContato_2());
            stmt.setString(6,p.getContato_3());
            stmt.execute();
            stmt.close();
            con.close();
            return true;

        }catch (SQLException ex){
            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Pessoa p){
        String sql = "UPDATE tb_pessoa SET nome = ?,sobrenome = ?, data_nascimento = '";
        LocalDate sql1 = p.getData_nascimento();
        String sql2 = "', parentesco = ? WHERE id=?;";


        try{
            PreparedStatement stmt = con.prepareStatement(sql+sql1+sql2);
            stmt.setString(1,p.getNome());
            stmt.setString(2,p.getSobrenome());
            stmt.setString(3,p.getParentesco());
            stmt.setInt(4,p.getId());
            stmt.execute();
            stmt.close();
            con.close();
            return true;

        }catch (SQLException ex){
            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean delete(Pessoa p){
        String sql = "DELETE FROM tb_pessoa WHERE nome=? AND sobrenome=? AND data_nascimento='"+p.getData_nascimento()+"' AND parentesco=? AND contato=?"+
                " AND contato1=? AND contato2=?";

        try{
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,p.getNome());
            stmt.setString(2,p.getSobrenome());
            stmt.setString(3,p.getParentesco());
            stmt.setString(4,p.getContato());
            stmt.setString(5,p.getContato_2());
            stmt.setString(6,p.getContato_3());
            stmt.execute();
            stmt.close();
            con.close();
            return true;

        }catch (SQLException ex){
            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public List<Pessoa> GetlList(){
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM tb_pessoa;";

        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Pessoa p = new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setSobrenome(rs.getString("sobrenome"));
                p.setData_nascimento(rs.getDate("data_nascimento").toLocalDate());
                p.setParentesco(rs.getString("parentesco"));
                pessoas.add(p);
            }
            stmt.close();
            rs.close();
            con.close();

            }catch (SQLException ex) {
                System.out.println("Erro, Lista não retornada");
                return null;
            }
            return pessoas;
    }

}