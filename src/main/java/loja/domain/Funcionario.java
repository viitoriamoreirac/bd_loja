package loja.domain;

public class Funcionario extends Pessoa {

    public Funcionario(String cpf, String nome, String endereco, String telefone) {
        super(cpf, nome, endereco, telefone);
    }

    @Override
    public String toString() {
        return "Funcionário {" + super.toString() + "}";
    }
}
