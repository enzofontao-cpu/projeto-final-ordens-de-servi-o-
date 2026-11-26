package br.senai.sistema.controller;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.senai.sistema.model.Cliente;
import br.senai.sistema.model.OrdemServico;
import br.senai.sistema.model.Tecnico;
import br.senai.sistema.service.ClienteService;
import br.senai.sistema.service.OrdemServicoService;
import br.senai.sistema.service.TecnicoService;

@Controller
public class PesquisaController {

    private final OrdemServicoService ordemServicoService;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;

    public PesquisaController(OrdemServicoService ordemServicoService,
                              ClienteService clienteService,
                              TecnicoService tecnicoService) {
        this.ordemServicoService = ordemServicoService;
        this.clienteService = clienteService;
        this.tecnicoService = tecnicoService;
    }

    @GetMapping("/pesquisa")
    public String pesquisar(@RequestParam(required = false, defaultValue = "") String q, Model model) {
        String termo = q.strip();
        String termoNormalizado = normalizar(termo);

        List<OrdemServico> ordens = termoNormalizado.isEmpty() ? List.of()
                : ordemServicoService.listarTodos().stream()
                        .filter(ordem -> corresponde(termoNormalizado,
                                ordem.getId(), ordem.getEquipamento(), ordem.getDefeitoRelatado(),
                                ordem.getDiagnostico(), ordem.getValor(), ordem.getDataAbertura(),
                                ordem.getStatus().name(), ordem.getStatus().getDescricao(),
                                ordem.getCliente().getNome(), ordem.getTecnico().getNome()))
                        .toList();
        List<Tecnico> tecnicos = termoNormalizado.isEmpty() ? List.of()
                : tecnicoService.listarTodos().stream()
                        .filter(tecnico -> corresponde(termoNormalizado,
                                tecnico.getId(), tecnico.getNome(), tecnico.getEspecialidade(), tecnico.getTelefone()))
                        .toList();
        List<Cliente> clientes = termoNormalizado.isEmpty() ? List.of()
                : clienteService.listarTodos().stream()
                        .filter(cliente -> corresponde(termoNormalizado,
                                cliente.getId(), cliente.getNome(), cliente.getTelefone(), cliente.getEmail(),
                                cliente.getCidade(), cliente.getBairro()))
                        .toList();

        model.addAttribute("q", termo);
        model.addAttribute("ordens", ordens);
        model.addAttribute("tecnicos", tecnicos);
        model.addAttribute("clientes", clientes);
        return "pesquisa/resultados";
    }

    private boolean corresponde(String termoNormalizado, Object... campos) {
        for (Object campo : campos) {
            if (campo != null && normalizar(campo.toString()).contains(termoNormalizado)) {
                return true;
            }
        }
        return false;
    }

    private String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .toLowerCase(Locale.ROOT);
    }
}
