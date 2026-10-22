package br.senai.sistema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidade Tecnico: representa o técnico que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "tecnicos" no banco.
 * As anotações de validação (@NotBlank, @Size...) definem as regras dos campos do formulário.
 */
@Entity
@Table(name = "tecnicos")
public class Tecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome do técnico. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 100, message = "Use no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    /** Tipo de equipamento que atende. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 60, message = "Use no máximo 60 caracteres.")
    @Column(nullable = false, length = 60)
    private String especialidade;

    /** Telefone para contato (opcional). */
    @Size(max = 20, message = "Use no máximo 20 caracteres.")
    @Column(length = 20)
    private String telefone;

    /** Construtor vazio: obrigatório para o JPA e para o formulário criarem objetos. */
    public Tecnico() {
    }

    /** Construtor com os dados principais: facilita criar objetos no código. */
    public Tecnico(String nome, String especialidade, String telefone) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.telefone = telefone;
    }

    // ---- Getters e setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
