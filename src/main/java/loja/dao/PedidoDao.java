package loja.dao;

import loja.db.ConnectionHelper;
import loja.domain.ItemPedido;
import loja.domain.Pedido;
import loja.domain.Produto;

import java.sql.*;
import java.util.*;

public class PedidoDao {

    public void save(Pedido pedido) {
        String sqlPedido = "INSERT INTO PEDIDO (CPF_CLIENTE_FK, CPF_FUNCIONARIO_FK, VALOR_TOTAL) VALUES (?, ?, ?)";
        String sqlItemPedido = "INSERT INTO ITEM_PEDIDO (ID_PEDIDO_FK, ID_PRODUTO_FK, QUANTIDADE, VALOR) VALUES (?, ?, ?, ?)";
        String sqlAtualizarProduto = "UPDATE PRODUTO SET QUANTIDADE = QUANTIDADE - ? WHERE ID = ?";

        try (Connection connection = ConnectionHelper.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement pstPedido = connection.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                pstPedido.setString(1, pedido.getCpfCliente());
                pstPedido.setString(2, pedido.getCpfFuncionario());
                pstPedido.setDouble(3, pedido.getValorTotal());
                pstPedido.executeUpdate();

                try (ResultSet rsPedido = pstPedido.getGeneratedKeys()) {
                    if (!rsPedido.next()) {
                        throw new SQLException("Erro ao criar pedido! Nenhum ID foi gerado.");
                    }
                    pedido.setId(rsPedido.getInt(1));
                }
            }

            try (PreparedStatement pstItemPedido = connection.prepareStatement(sqlItemPedido);
                 PreparedStatement pstAtualizarProduto = connection.prepareStatement(sqlAtualizarProduto)) {

                for (ItemPedido item : pedido.getItens()) {
                    Produto produto = item.getProduto();
                    if (produto == null) {
                        throw new SQLException("Produto inválido para o item do pedido!");
                    }

                    pstItemPedido.setInt(1, pedido.getId());
                    pstItemPedido.setInt(2, produto.getId());
                    pstItemPedido.setInt(3, item.getQuantidade());
                    pstItemPedido.setDouble(4, item.getQuantidade() * produto.getValorUnit());
                    pstItemPedido.executeUpdate();

                    pstAtualizarProduto.setInt(1, item.getQuantidade());
                    pstAtualizarProduto.setInt(2, produto.getId());
                    pstAtualizarProduto.executeUpdate();
                }
            }

            connection.commit();
            System.out.println("Pedido cadastrado com sucesso! ID: " + pedido.getId());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pedido!", e);
        }
    }

    public List<Pedido> findAll() {
        String sql = """
            SELECT p.*, ip.ID AS ITEM_ID, ip.ID_PRODUTO_FK, ip.QUANTIDADE, ip.VALOR, 
                   pr.NOME, pr.VALOR_UNIT, pr.QUANTIDADE AS ESTOQUE 
            FROM PEDIDO p 
            LEFT JOIN ITEM_PEDIDO ip ON p.ID = ip.ID_PEDIDO_FK
            LEFT JOIN PRODUTO pr ON ip.ID_PRODUTO_FK = pr.ID;
        """;

        Map<Integer, Pedido> pedidosMap = new HashMap<>();

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int idPedido = rs.getInt("ID");
                Pedido pedido = pedidosMap.computeIfAbsent(idPedido, id -> {
                    try {
                        return new Pedido(
                                id,
                                rs.getString("CPF_CLIENTE_FK"),
                                rs.getString("CPF_FUNCIONARIO_FK"),
                                new ArrayList<>(),
                                rs.getDouble("VALOR_TOTAL")
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                ItemPedido item = criarItemPedido(rs, pedido);
                if (item != null) {
                    pedido.getItens().add(item);
                }
            }
            return new ArrayList<>(pedidosMap.values());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pedidos no banco de dados", e);
        }
    }

    public Pedido findById(int id) {
        String sql = """
            SELECT p.*, ip.ID AS ITEM_ID, ip.ID_PRODUTO_FK, ip.QUANTIDADE, ip.VALOR, 
                   pr.NOME, pr.VALOR_UNIT, pr.QUANTIDADE AS ESTOQUE 
            FROM PEDIDO p 
            LEFT JOIN ITEM_PEDIDO ip ON p.ID = ip.ID_PEDIDO_FK
            LEFT JOIN PRODUTO pr ON ip.ID_PRODUTO_FK = pr.ID
            WHERE p.ID = ?;
        """;

        Pedido pedido = null;

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    if (pedido == null) {
                        pedido = new Pedido(
                                rs.getInt("ID"),
                                rs.getString("CPF_CLIENTE_FK"),
                                rs.getString("CPF_FUNCIONARIO_FK"),
                                new ArrayList<>(),
                                rs.getDouble("VALOR_TOTAL")
                        );
                    }

                    ItemPedido item = criarItemPedido(rs, pedido);
                    if (item != null) {
                        pedido.getItens().add(item);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido no banco de dados", e);
        }

        return pedido;
    }

    private ItemPedido criarItemPedido(ResultSet rs, Pedido pedido) throws SQLException {
        if (rs.getInt("ITEM_ID") == 0) {
            return null;
        }

        Produto produto = new Produto(
                rs.getInt("ID_PRODUTO_FK"),
                rs.getString("NOME"),
                rs.getDouble("VALOR_UNIT"),
                rs.getInt("ESTOQUE")
        );

        return new ItemPedido(
                rs.getInt("ITEM_ID"),
                pedido.getId(),
                produto,
                rs.getInt("QUANTIDADE"),
                rs.getDouble("VALOR")
        );
    }
}
