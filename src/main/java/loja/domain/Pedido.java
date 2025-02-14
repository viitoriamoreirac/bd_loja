package loja.domain;

import java.util.List;

public class Pedido {
    private int id;
    private String cpfCliente;
    private String cpfFuncionario;
    private List<ItemPedido> itens;
    private double valorTotal;

    public Pedido(int id, String cpfCliente, String cpfFuncionario, List<ItemPedido> itens, double valorTotal) {
        this.id = id;
        this.cpfCliente = cpfCliente;
        this.cpfFuncionario = cpfFuncionario;
        this.itens = itens;
        this.valorTotal = valorTotal;
    }

    public Pedido(String cpfCliente, String cpfFuncionario, List<ItemPedido> itens, double valorTotal) {
        this.cpfCliente = cpfCliente;
        this.cpfFuncionario = cpfFuncionario;
        this.itens = itens;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCpfCliente() {
        return cpfCliente;
    }
    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }
    public String getCpfFuncionario() {
        return cpfFuncionario;
    }
    public void setCpfFuncionario(String cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }
    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Venda '" + id + '\'' +
                "Realizada por '" + cpfFuncionario + '\''+
                "Vendido para '" + cpfCliente + '\''+
                '}';
    }
}
