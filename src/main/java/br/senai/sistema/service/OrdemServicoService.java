package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.OrdemServico;
import br.senai.sistema.repository.OrdemServicoRepository;

/**
 * Camada de serviço de OrdemServico: concentra as operações e, mais adiante, as regras de negócio.
 */
@Service
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;

    // Injeção de dependência: o Spring entrega o repositório pronto pelo construtor.
    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
    }

    /** Lista todos os registros, em ordem de abertura. */
    public List<OrdemServico> listarTodos() {
        return ordemServicoRepository.findAll(Sort.by("dataAbertura"));
    }

    /** Busca um registro pelo id. Se não existir, gera um erro com mensagem clara. */
    public OrdemServico buscarPorId(Long id) {
        return ordemServicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada: " + id));
    }

    /** Salva: se o id for vazio, cadastra um novo; se tiver id, atualiza o existente. */
    public OrdemServico salvar(OrdemServico ordemServico) {
        return ordemServicoRepository.save(ordemServico);
    }

    /** Exclui pelo id. */
    public void excluir(Long id) {
        ordemServicoRepository.deleteById(id);
    }
}