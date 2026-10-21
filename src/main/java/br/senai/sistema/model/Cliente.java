package br.senai.sistema.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

/**
 * Entidade Cliente: representa o cliente que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "clientes" no banco.
 * As anotações de validação (@NotBlank, @Size...) definem as regras dos campos do formulário.
 */
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome da pessoa ou empresa. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 100, message = "Use no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    /** Telefone para contato. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 20, message = "Use no máximo 20 caracteres.")
    @Column(nullable = false, length = 20)
    private String telefone;

    /** E-mail (opcional). */
    @Size(max = 100, message = "Use no máximo 100 caracteres.")
    @Email(message = "Informe um e-mail válido.")
    @Column(length = 100)
    private String email;

    /** Cidade do cliente. */
    @Size(max = 60, message = "Use no máximo 60 caracteres.")
    @Column(length = 60)
    private String cidade;

    /** Data em que o cliente foi cadastrado. */
    @NotNull(message = "Campo obrigatório.")
    @PastOrPresent(message = "A data não pode ser futura.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate dataCadastro;

    /** Construtor vazio: obrigatório para o JPA e para o formulário criarem objetos. */
    public Cliente() {
    }

    /** Construtor com os dados principais: facilita criar objetos no código. */
    public Cliente(String nome, String telefone, String email, String cidade, LocalDate dataCadastro) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
        this.dataCadastro = dataCadastro;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
