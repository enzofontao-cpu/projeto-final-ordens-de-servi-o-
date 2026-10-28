package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sistema.model.OrdemServico;
import br.senai.sistema.model.StatusOrdem;
import br.senai.sistema.repository.OrdemServicoRepository;

/**
 * Camada de serviço de OrdemServico: concentra as operações e as REGRAS DE NEGÓCIO.
 * Quando uma regra não é cumprida, o método lança RegraNegocioException com a mensagem para o usuário.
 */
@Service
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;

    // Injeção de dependência: o Spring entrega os repositórios prontos pelo construtor.
    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
    }

    /** Lista todos os registros, em ordem de abertura. */
    public List<OrdemServico> listarTodos() {
        return ordemServicoRepository.findAll(Sort.by("dataAbertura"));
    }

    /** Lista os registros de uma situação; sem filtro (null), lista todos. */
    public List<OrdemServico> listar(StatusOrdem status) {
        if (status == null) {
            return listarTodos();
        }
        return ordemServicoRepository.findByStatus(status, Sort.by("dataAbertura"));
    }

    /** Busca um registro pelo id. Se não existir, gera um erro com mensagem clara. */
    public OrdemServico buscarPorId(Long id) {
        return ordemServicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada: " + id));
    }

    /**
     * Salva. Ordem nova começa ABERTA. Na edição, a situação não muda pelo formulário
     * e uma ordem CONCLUÍDA não pode ser alterada (regra de negócio 2).
     */
    @Transactional
    public OrdemServico salvar(OrdemServico ordemServico) {
        if (ordemServico.getId() == null) {
            ordemServico.setStatus(StatusOrdem.ABERTA);
        } else {
            OrdemServico atual = buscarPorId(ordemServico.getId());
            verificarSeConcluida(atual);
            ordemServico.setStatus(atual.getStatus());
        }
        return ordemServicoRepository.save(ordemServico);
    }

    /** Muda a situação (Iniciar ou Concluir), conferindo as regras de negócio. */
    @Transactional
    public void mudarStatus(Long id, StatusOrdem novoStatus) {
        OrdemServico ordemServico = buscarPorId(id);
        verificarSeConcluida(ordemServico);                                    // regra 2
        if (novoStatus == StatusOrdem.CONCLUIDA) {                          // regra 1
            boolean semDiagnostico = ordemServico.getDiagnostico() == null || ordemServico.getDiagnostico().isBlank();
            if (semDiagnostico || ordemServico.getValor() == null) {
                throw new RegraNegocioException("Para concluir a ordem de serviço, informe o diagnóstico e o valor (use Editar).");
            }
        }
        ordemServico.setStatus(novoStatus);
        ordemServicoRepository.save(ordemServico);
    }

    /** Exclui pelo id. Uma ordem concluída não pode ser excluída (regra 2). */
    @Transactional
    public void excluir(Long id) {
        verificarSeConcluida(buscarPorId(id));
        ordemServicoRepository.deleteById(id);
    }

    /** Regra de negócio 2: uma ordem de serviço concluída não pode mais ser alterada. */
    private void verificarSeConcluida(OrdemServico ordemServico) {
        if (ordemServico.getStatus() == StatusOrdem.CONCLUIDA) {
            throw new RegraNegocioException("Ordem de serviço concluída não pode ser alterada nem excluída.");
        }
    }
}