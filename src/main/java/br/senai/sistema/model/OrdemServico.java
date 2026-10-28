package br.senai.sistema.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

/**
 * Entidade OrdemServico: representa a ordem de serviço que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "ordens_servico" no banco.
 * As anotações de validação (@NotBlank, @Size...) definem as regras dos campos do formulário.
 * Os atributos com @ManyToOne guardam, no banco, só o id do registro relacionado (chave estrangeira).
 */
@Entity
@Table(name = "ordens_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Equipamento deixado para conserto. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 100, message = "Use no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String equipamento;

    /** Problema informado pelo cliente. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 255, message = "Use no máximo 255 caracteres.")
    @Column(nullable = false, length = 255)
    private String defeitoRelatado;

    /** Diagnóstico do técnico (opcional). */
    @Size(max = 255, message = "Use no máximo 255 caracteres.")
    @Column(length = 255)
    private String diagnostico;

    /** Valor do serviço (opcional). */
    @DecimalMin(value = "0.00", message = "O valor não pode ser negativo.")
    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    /** Data de abertura da ordem. */
    @NotNull(message = "Campo obrigatório.")
    @PastOrPresent(message = "A data não pode ser futura.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate dataAbertura;

    /** Situação (aberta, em andamento, concluída). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusOrdem status;

    /** Cliente que solicitou o serviço: relacionamento N:1 (muitos registros daqui apontam para um Cliente). */
    @NotNull(message = "Selecione um cliente.")
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /** Técnico responsável: relacionamento N:1 (muitos registros daqui apontam para um Tecnico). */
    @NotNull(message = "Selecione um técnico.")
    @ManyToOne
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    /** Construtor vazio: obrigatório para o JPA e para o formulário criarem objetos. */
    public OrdemServico() {
    }

    /** Construtor com os dados principais: facilita criar objetos no código. */
    public OrdemServico(String equipamento, String defeitoRelatado, LocalDate dataAbertura, StatusOrdem status, Cliente cliente, Tecnico tecnico) {
        this.equipamento = equipamento;
        this.defeitoRelatado = defeitoRelatado;
        this.dataAbertura = dataAbertura;
        this.status = status;
        this.cliente = cliente;
        this.tecnico = tecnico;
    }

    // ---- Getters e setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getDefeitoRelatado() {
        return defeitoRelatado;
    }

    public void setDefeitoRelatado(String defeitoRelatado) {
        this.defeitoRelatado = defeitoRelatado;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public StatusOrdem getStatus() {
        return status;
    }

    public void setStatus(StatusOrdem status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }
}