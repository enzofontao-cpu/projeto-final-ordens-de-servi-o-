package br.senai.sistema.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sistema.model.Ocorrencia;
import br.senai.sistema.repository.OcorrenciaRepository;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    @Transactional(readOnly = true)
    public List<Ocorrencia> listar() {
        return ocorrenciaRepository.findAllByOrderByDataRegistroDesc();
    }

    @Transactional
    public void registrar(Ocorrencia ocorrencia, String usuario) {
        ocorrencia.setUsuario(usuario);
        ocorrencia.setDataRegistro(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        ocorrenciaRepository.save(ocorrencia);
    }
}
