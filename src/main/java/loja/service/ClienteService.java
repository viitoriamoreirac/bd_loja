package loja.service;

import loja.dao.ClienteDao;
import loja.domain.Cliente;
import loja.utils.Validador;

public class ClienteService {
    private ClienteDao clienteDao;

    public ClienteService(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public void cadastrarCliente(String cpf, String nome, String endereco, String telefone) {

        if (!Validador.validarCpf(cpf)) {
            throw new RuntimeException("Erro: CPF inválido!");
        }
        if (!Validador.validarNome(nome)) {
            throw new RuntimeException("Erro: Nome inválido!");
        }
        if (!Validador.validarEndereco(endereco)) {
            throw new RuntimeException("Erro: Endereço inválido!");
        }
        if (!Validador.validarTelefone(telefone)) {
            throw new RuntimeException("Erro: Telefone inválido!");
        }
        System.out.println(clienteDao.findByCpf(cpf));
        if (clienteDao.findByCpf(cpf) == null) {
            Cliente cliente = new Cliente(cpf, nome, endereco, telefone);
            clienteDao.save(cliente);
        } else {
            throw new RuntimeException("O cliente já existe no sistema!");
        }
    }

    public Cliente buscarCliente(String cpf) {
        if (!Validador.validarCpf(cpf)) {
            throw new IllegalArgumentException("CPF do cliente inválido.");
        }

        Cliente cliente = clienteDao.findByCpf(cpf);

        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        return cliente;
    }
}
