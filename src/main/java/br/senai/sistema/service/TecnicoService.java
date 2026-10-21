package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Tecnico;
import br.senai.sistema.repository.TecnicoRepository;

/**
 * Camada de serviço de Tecnico: concentra as operações e, mais adiante, as regras de negócio.
 */
@Service
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;

    // Injeção de dependência: o Spring entrega o repositório pronto pelo construtor.
    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    /** Lista todos os registros, em ordem alfabética. */
    public List<Tecnico> listarTodos() {
        return tecnicoRepository.findAll(Sort.by("nome"));
    }

    /** Busca um registro pelo id. Se não existir, gera um erro com mensagem clara. */
    public Tecnico buscarPorId(Long id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Técnico não encontrado: " + id));
    }

    /** Salva: se o id for vazio, cadastra um novo; se tiver id, atualiza o existente. */
    public Tecnico salvar(Tecnico tecnico) {
        return tecnicoRepository.save(tecnico);
    }

    /** Exclui pelo id. */
    public void excluir(Long id) {
        tecnicoRepository.deleteById(id);
    }
}