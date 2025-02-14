package loja.service;

import loja.dao.ProdutoDao;
import loja.domain.Produto;
import loja.utils.Validador;

import java.util.ArrayList;
import java.util.List;

public class ProdutoService {
    private ProdutoDao produtoDao;

    public ProdutoService(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public void cadastrarProduto(String nome, double valorUnit, int quantidade ) {

        if (!Validador.validarNome(nome)) {
            throw new RuntimeException("Erro: Nome inválido!");
        }

        if (!Validador.validarValor(valorUnit)) {
            throw new RuntimeException("Erro: Valor inválido!");
        }
        if (!Validador.validarQuantidade(quantidade)) {
            throw new RuntimeException("Erro: Quantidade inválida!");
        }

        Produto produto = new Produto(nome, valorUnit, quantidade);

        produtoDao.save(produto);
    }

    public Produto buscarProdutoPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }

        Produto produto = produtoDao.findById(id);

        if (produto == null) {
            throw new RuntimeException("Produto não encontrado.");
        }

        return produto;
    }

    public List<Produto> listarProdutosDisponiveis() {
        List<Produto> produtos = produtoDao.findAll();
        List<Produto> produtosDisponiveis = new ArrayList<>();

        for (Produto produto : produtos) {
            if (produto.getQuantidade() > 0) {
                produtosDisponiveis.add(produto);
            }
        }
        return produtosDisponiveis;
    }
}

