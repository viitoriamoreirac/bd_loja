package loja.service;

import loja.dao.PedidoDao;
import loja.domain.ItemPedido;
import loja.domain.Pedido;
import loja.domain.Produto;
import loja.utils.Validador;
import java.util.List;

public class PedidoService {
    private PedidoDao pedidoDao;
    private ProdutoService produtoService;

    public PedidoService(PedidoDao pedidoDao, ClienteService clienteService, FuncionarioService funcionarioService, ProdutoService produtoService) {
        this.pedidoDao = pedidoDao;
        this.produtoService = produtoService;
    }

    public void efetuarVenda(String cpfCliente, String cpfFuncionario, List<ItemPedido> itens, double valorTotal) {
        if (!Validador.validarQtdItens(itens)) {
            throw new RuntimeException("Erro: Não é possível criar um pedido vazio.");
        }
        for (ItemPedido item : itens) {
            Produto produto = produtoService.buscarProdutoPorId(item.getId());

            if (produto.getQuantidade() < item.getQuantidade()) {
                throw new IllegalArgumentException("Erro: Estoque insuficiente para o produto " + produto.getNome());
            }
        }
        Pedido pedido = new Pedido(cpfCliente, cpfFuncionario, itens, valorTotal);
        pedidoDao.save(pedido);
    }
    public List<Pedido> listarVendas() {
        List<Pedido> pedidosFeitos = pedidoDao.findAll();

        return pedidosFeitos;
    }
}
