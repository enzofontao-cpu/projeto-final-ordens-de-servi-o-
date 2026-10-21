package br.senai.sistema.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade Cliente: representa o cliente que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "clientes" no banco.
 * Cada atributo vira UMA COLUNA dessa tabela.
 */
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome da pessoa ou empresa. */
    @Column(nullable = false, length = 100)
    private String nome;

    /** Telefone para contato. */
    @Column(nullable = false, length = 20)
    private String telefone;

    /** E-mail (opcional). */
    @Column(length = 100)
    private String email;

    /** Cidade do cliente. */
    @Column(length = 60)
    private String cidade;

    /** Data em que o cliente foi cadastrado. */
    @Column(nullable = false)
    private LocalDate dataCadastro;

    /** Construtor vazio: obrigatório para o JPA criar objetos ao ler do banco. */
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

    // ---- Getters e setters: a forma de ler e alterar os atributos privados ----

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
