package loja.utils;

import loja.domain.ItemPedido;

import java.util.List;

public class Validador {

    public static boolean validarCpf(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }

    public static boolean validarNome(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }

    public static boolean validarTelefone(String telefone) {
        return telefone != null && telefone.matches("\\d{9,15}");
    }

    public static boolean validarEndereco(String endereco) {
        return endereco != null && !endereco.trim().isEmpty();
    }

    public static boolean validarQuantidade(int quantidade) {
        return quantidade >= 0;
    }

    public static boolean validarValor(double valor) {
        return valor > 0;
    }

    public static boolean validarQtdItens(List<ItemPedido> itens) {
        return !(itens == null || itens.isEmpty());
    }
}

