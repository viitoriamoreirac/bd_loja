package loja.app;

import loja.dao.*;
import loja.service.*;

public class Main {
    public static void main(String[] args) {
        ClienteDao clienteDao = new ClienteDao();
        ProdutoDao produtoDao = new ProdutoDao();
        PedidoDao pedidoDao = new PedidoDao();
        FuncionarioDao funcionarioDao = new FuncionarioDao();
        ProdutoService produtoService = new ProdutoService(produtoDao);
        ClienteService clienteService = new ClienteService(clienteDao);
        FuncionarioService funcionarioService = new FuncionarioService(funcionarioDao);
        PedidoService pedidoService = new PedidoService(pedidoDao, clienteService, funcionarioService, produtoService);

        MenuPrincipal menuPrincipal = new MenuPrincipal(produtoService, clienteService, pedidoService);
        menuPrincipal.exibirMenuPrincipal();


    }
}
