package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Cliente;
import br.senai.sistema.repository.ClienteRepository;

/**
 * Camada de serviço de Cliente: concentra as operações e, mais adiante, as regras de negócio.
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

    /** Busca um registro pelo id. Se não existir, gera um erro com mensagem clara. */
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
    }

    /** Salva: se o id for vazio, cadastra um novo; se tiver id, atualiza o existente. */
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /** Exclui pelo id. */
    public void excluir(Long id) {
        clienteRepository.deleteById(id);
    }
}
