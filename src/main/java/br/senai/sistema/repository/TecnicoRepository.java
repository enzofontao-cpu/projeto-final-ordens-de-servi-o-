package br.senai.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Tecnico;

/**
 * Acesso ao banco de dados para a entidade Tecnico.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<Tecnico, Long>  ->  entidade Tecnico, chave primária do tipo Long.
 */
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
}
