package loja.domain;

public class Cliente extends Pessoa {

    public Cliente(String cpf, String nome, String endereco, String telefone) {

        super(cpf, nome, endereco, telefone);
    }

    @Override
    public String toString() {
        return "Cliente{" + super.toString() + "}";
    }
}
