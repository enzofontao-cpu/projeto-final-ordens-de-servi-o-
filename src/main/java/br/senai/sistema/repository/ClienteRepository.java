package br.senai.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Cliente;

/**
 * Acesso ao banco de dados para a entidade Cliente.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<Cliente, Long>  ->  entidade Cliente, chave primária do tipo Long.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
