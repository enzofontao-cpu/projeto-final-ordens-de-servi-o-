package br.senai.sistema.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ocorrencias")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Selecione o tipo do registro.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoOcorrencia tipo;

    @NotBlank(message = "Informe onde a ocorrência foi identificada.")
    @Size(max = 150, message = "Use no máximo 150 caracteres.")
    @Column(nullable = false, length = 150)
    private String local;

    @NotBlank(message = "Descreva os passos para reproduzir.")
    @Size(max = 2000, message = "Use no máximo 2000 caracteres.")
    @Column(nullable = false, length = 2000)
    private String passos;

    @NotBlank(message = "Descreva o que aconteceu.")
    @Size(max = 2000, message = "Use no máximo 2000 caracteres.")
    @Column(nullable = false, length = 2000)
    private String ocorrido;

    @NotBlank(message = "Descreva o que era esperado.")
    @Size(max = 2000, message = "Use no máximo 2000 caracteres.")
    @Column(nullable = false, length = 2000)
    private String esperado;

    @Column(nullable = false, length = 100)
    private String usuario;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;

    public Long getId() {
        return id;
    }

    public TipoOcorrencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoOcorrencia tipo) {
        this.tipo = tipo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getPassos() {
        return passos;
    }

    public void setPassos(String passos) {
        this.passos = passos;
    }

    public String getOcorrido() {
        return ocorrido;
    }

    public void setOcorrido(String ocorrido) {
        this.ocorrido = ocorrido;
    }

    public String getEsperado() {
        return esperado;
    }

    public void setEsperado(String esperado) {
        this.esperado = esperado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
