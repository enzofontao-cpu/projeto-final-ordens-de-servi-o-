package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Cliente;
import br.senai.sistema.repository.ClienteRepository;

/**
 * Camada de serviço de Cliente: é aqui que ficarão as regras de negócio.
 * Por enquanto, ela só busca os dados no repositório.
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // Injeção de dependência: o Spring entrega o repositório pronto pelo construtor.
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /** Lista todos os registros, em ordem alfabética. */
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll(Sort.by("nome"));
    }
}
