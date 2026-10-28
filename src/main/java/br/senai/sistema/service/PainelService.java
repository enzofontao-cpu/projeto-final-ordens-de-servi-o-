package br.senai.sistema.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Indicador;
import br.senai.sistema.model.OrdemServico;
import br.senai.sistema.model.StatusOrdem;
import br.senai.sistema.repository.OrdemServicoRepository;

/**
 * Calcula os indicadores do painel (página inicial).
 * Usa consultas de contagem do Spring Data (count, countBy...) e laços (for) para contar e somar.
 */
@Service
public class PainelService {

    private final OrdemServicoRepository ordemServicoRepository;

    public PainelService(OrdemServicoRepository ordemServicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
    }

    /** Data de hoje no horário de Brasília (o Codespace usa o horário de Londres). */
    private LocalDate hoje() {
        return LocalDate.now(ZoneId.of("America/Sao_Paulo"));
    }

    /** Os indicadores do painel, na ordem em que aparecem. */
    public List<Indicador> indicadores() {
        long abertas = ordemServicoRepository.countByStatus(StatusOrdem.ABERTA);
        long emAndamento = ordemServicoRepository.countByStatus(StatusOrdem.EM_ANDAMENTO);
        long concluidas = ordemServicoRepository.countByStatus(StatusOrdem.CONCLUIDA);

        // Faturamento: soma do valor das ordens concluídas (percorre a lista e soma)
        BigDecimal faturamento = BigDecimal.ZERO;
        for (OrdemServico ordem : ordemServicoRepository.findByStatus(StatusOrdem.CONCLUIDA, Sort.by("dataAbertura"))) {
            if (ordem.getValor() != null) {
                faturamento = faturamento.add(ordem.getValor());
            }
        }
        NumberFormat dinheiro = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

        List<Indicador> lista = new ArrayList<>();
        lista.add(new Indicador("Abertas", String.valueOf(abertas), "aguardando início", "bi-inbox", "secondary"));
        lista.add(new Indicador("Em andamento", String.valueOf(emAndamento), "em conserto", "bi-tools", "warning"));
        lista.add(new Indicador("Concluídas", String.valueOf(concluidas), "prontas", "bi-check2-circle", "success"));
        lista.add(new Indicador("Faturamento", dinheiro.format(faturamento), "soma das ordens concluídas", "bi-cash-stack", "primary"));
        return lista;
    }

    /** Lista de atenção: ordens ainda não concluídas, das mais antigas para as mais novas. */
    public List<OrdemServico> atencao() {
        return ordemServicoRepository.findByStatusNotOrderByDataAberturaAsc(StatusOrdem.CONCLUIDA);
    }
}