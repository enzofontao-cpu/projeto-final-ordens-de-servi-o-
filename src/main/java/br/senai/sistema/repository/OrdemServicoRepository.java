package br.senai.sistema.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.OrdemServico;
import br.senai.sistema.model.StatusOrdem;

/**
 * Acesso ao banco de dados para a entidade OrdemServico.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<OrdemServico, Long>  ->  entidade OrdemServico, chave primária do tipo Long.
 */
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    // Filtro: registros de uma situação, na ordem indicada
    List<OrdemServico> findByStatus(StatusOrdem status, Sort ordem);

    // Painel: quantas ordens em uma situação
    long countByStatus(StatusOrdem status);

    // Painel: ordens que ainda não foram concluídas, das mais antigas para as mais novas
    List<OrdemServico> findByStatusNotOrderByDataAberturaAsc(StatusOrdem status);
}