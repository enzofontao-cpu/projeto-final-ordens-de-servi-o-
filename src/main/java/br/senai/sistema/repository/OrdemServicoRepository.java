package br.senai.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.OrdemServico;

/**
 * Acesso ao banco de dados para a entidade OrdemServico.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<OrdemServico, Long>  ->  entidade OrdemServico, chave primária do tipo Long.
 */
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
}