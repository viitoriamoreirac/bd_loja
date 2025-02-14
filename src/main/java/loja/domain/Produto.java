package loja.domain;

public class Produto {
    private int id;
    private String nome;
    private double valorUnit;
    private int quantidade;

    public Produto(int id, String nome, double valorUnit, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.valorUnit = valorUnit;
        this.quantidade = quantidade;
    }

    public Produto(String nome, double valorUnit, int quantidade) {
        this.nome = nome;
        this.valorUnit = valorUnit;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public double getValorUnit() {
        return valorUnit;
    }
    public void setValorUnit(double valorUnit) {
        this.valorUnit = valorUnit;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", valor unitário='" + valorUnit + '\'' +
                ", quantidade disponível='" + quantidade + '\'' +
                '}';
    }

}
