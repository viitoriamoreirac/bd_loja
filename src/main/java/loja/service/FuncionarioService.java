package loja.service;

import loja.dao.FuncionarioDao;
import loja.domain.Funcionario;
import loja.utils.Validador;

public class FuncionarioService {
    private FuncionarioDao funcionarioDao;

    public FuncionarioService(FuncionarioDao funcionarioDao) {
        this.funcionarioDao = funcionarioDao;
    }

    public Funcionario buscarFuncionario(String cpf) {
        if (!Validador.validarCpf(cpf)) {
            throw new IllegalArgumentException("CPF do funcionario inválido.");
        }

        Funcionario funcionario = funcionarioDao.findByCpf(cpf);

        if (funcionario == null) {
            throw new RuntimeException("Funcionario não encontrado.");
        }

        return funcionario;
    }
}
