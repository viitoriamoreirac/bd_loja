package loja.dao;

import loja.db.ConnectionHelper;
import loja.domain.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao {

    public void save(Funcionario funcionario) {
        String sql = "INSERT INTO FUNCIONARIO VALUES (?,?,?,?);";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)){

            pst.setString(1, funcionario.getCpf());
            pst.setString(2, funcionario.getNome());
            pst.setString(3, funcionario.getEndereco());
            pst.setString(4, funcionario.getTelefone());

            pst.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionario", e);
        }
    }

    public List<Funcionario> findAll() {
        String sql = "SELECT * FROM FUNCIONARIO;";

        List<Funcionario> lista = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()){

            while (rs.next()) {
                String cpf = rs.getString("CPF");
                String nome = rs.getString("NOME");
                String endereco = rs.getString("ENDERECO");
                String telefone = rs.getString("TELEFONE");

                Funcionario funcionario = new Funcionario(cpf, nome, endereco, telefone);
                lista.add(funcionario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionarios", e);
        }
        return lista;
    }

    public Funcionario findByCpf(String cpf) {
        String sql = "SELECT * FROM FUNCIONARIO WHERE CPF = ?;";
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, cpf);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Funcionario(
                        rs.getString("CPF"),
                        rs.getString("NOME"),
                        rs.getString("ENDERECO"),
                        rs.getString("TELEFONE")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionario", e);
        }
        return null;
    }
}
