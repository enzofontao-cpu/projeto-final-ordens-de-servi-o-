package br.senai.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Ocorrencia;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    List<Ocorrencia> findAllByOrderByDataRegistroDesc();
}
