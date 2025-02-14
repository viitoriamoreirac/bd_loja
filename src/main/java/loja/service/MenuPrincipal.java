package loja.service;

import loja.domain.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuPrincipal {
    private ProdutoService produtoService;
    private ClienteService clienteService;
    private PedidoService pedidoService;
    private Scanner sc;

    public MenuPrincipal(ProdutoService produtoService, ClienteService clienteService, PedidoService pedidoService) {
        this.produtoService = produtoService;
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
        this.sc = new Scanner(System.in);
    }

    public void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("------------Menu Principal------------");
            System.out.println("1. Cadastrar produtos");
            System.out.println("2. Cadastrar cliente");
            System.out.println("3. Buscar produto");
            System.out.println("4. Listar produtos disponíveis");
            System.out.println("5. Efetuar venda");
            System.out.println("6. Listar vendas realizadas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 3:
                    buscarProduto();
                    break;
                case 4:
                    listarProdutosDisponiveis();
                    break;
                case 5:
                    efetuarVenda();
                    break;
                case 6:
                    listarVendas();
                    break;
                case 0:
                    System.out.println("Saindo");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void cadastrarProduto() {
        System.out.println("Qual será o nome do produto? ");
        String nome = sc.nextLine();
        System.out.println("Qual o preço? ");
        double valorUnit = sc.nextDouble();
        System.out.println("Quantas unidades têm disponíveis? ");
        int quantidade = sc.nextInt();

        produtoService.cadastrarProduto(nome, valorUnit, quantidade);
        System.out.println("Produto cadastrado com sucesso!");
    }

    public void cadastrarCliente() {
        System.out.println("Qual o CPF do cliente? ");
        String cpf = sc.nextLine();
        System.out.println("Qual o nome do cliente? ");
        String nome = sc.nextLine();
        System.out.println("Qual o endereço do cliente? ");
        String endereco = sc.nextLine();
        System.out.println("Qual o telefone do cliente? ");
        String telefone = sc.nextLine();

        clienteService.cadastrarCliente(cpf, nome, endereco, telefone);
        System.out.println("Cliente cadastrado com sucesso!");
        }

    public Produto buscarProduto() {
        System.out.println("Digite o ID do produto: ");
        int id = sc.nextInt();
        Produto produto = produtoService.buscarProdutoPorId(id);
        System.out.println(produto.toString());

        return produto;
    }

    public void listarProdutosDisponiveis() {
        List<Produto> produtosDisponiveis = produtoService.listarProdutosDisponiveis();
        for (Produto produto : produtosDisponiveis) {
            System.out.println(produto.toString());
        }
    }
    public void efetuarVenda() {
        System.out.println("Qual o CPF do cliente? ");
        String cpfCliente = sc.nextLine();
        System.out.println("Qual o CPF do funcionário? ");
        String cpfFuncionario = sc.nextLine();
        double valorTotal = 0;
        int opcao = 1;
        List<ItemPedido> itens = new ArrayList<>();

        do {
            Produto produto = buscarProduto();
            System.out.println("Digite a quantidade a ser comprada: ");
            int quantidade = sc.nextInt();
            double valor = 0;

            ItemPedido item = new ItemPedido (produto, quantidade, valor);
            itens.add(item);

            System.out.println("Deseja adicionar mais algum produto? 1 para sim e 0 para não.");
            opcao = sc.nextInt();

            if (opcao != 1 && opcao != 0){
                System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        pedidoService.efetuarVenda(cpfCliente, cpfFuncionario, itens, valorTotal);


    }
    public void listarVendas() {
        List<Pedido> vendasFeitas = pedidoService.listarVendas();

        for (Pedido pedido : vendasFeitas) {
            System.out.println(pedido.toString());
        }
    }
}
