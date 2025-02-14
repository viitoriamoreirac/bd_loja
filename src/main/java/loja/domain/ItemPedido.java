package loja.domain;

public class ItemPedido {
    private int id;
    private int idPedido;
    private Produto produto;
    private int quantidade;
    private double valor;

    public ItemPedido(int id, int idPedido, Produto produto, int quantidade, double valor) {
        this.id = id;
        this.idPedido = idPedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public ItemPedido(Produto produto, int quantidade, double valor) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public double getValor() {
        return quantidade * produto.getValorUnit();
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Item: {" +
                "id= " + id +
                "Produto= '" + produto.getNome() + '\'' +
                "Quantidade= '" + quantidade + '\'' +
                "Total= '" + valor + '\'' +
                '}';
    }
}
