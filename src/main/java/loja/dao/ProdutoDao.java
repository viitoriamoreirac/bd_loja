package loja.dao;

import loja.db.ConnectionHelper;
import loja.domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {

    public void save(Produto produto) {
        String sql = "INSERT INTO PRODUTO (NOME, VALOR_UNIT, QUANTIDADE) VALUES (?,?,?);";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);){

            pst.setString(1, produto.getNome());
            pst.setDouble(2, produto.getValorUnit());
            pst.setInt(3, produto.getQuantidade());

            pst.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Produto> findAll() {
        String sql = "SELECT * FROM PRODUTO;";

        List<Produto> lista = new ArrayList<Produto>();
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery();){

            while (rs.next()){
                int id = rs.getInt("ID");
                String nome = rs.getString("NOME");
                double valor_unit = rs.getDouble("VALOR_UNIT");
                int quantidade = rs.getInt("QUANTIDADE");

                Produto produto = new Produto(id, nome, valor_unit, quantidade);
                lista.add(produto);
            }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao listar produtos",e);
            }

            return lista;
    }

    public Produto findById(int id) {
        String sql = "SELECT * FROM PRODUTO WHERE ID = ?;";
        try (Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Produto(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("VALOR_UNIT"),
                        rs.getInt("QUANTIDADE")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar o produto",e);
        }
        return null;
    }

    public void update(Produto produto) {
        String sql = "UPDATE PRODUTO SET QUANTIDADE = ? WHERE ID = ?;";
        try(Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setInt(1, produto.getQuantidade());
            pst.setInt(2, produto.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o produto",e);
        }
    }

}
