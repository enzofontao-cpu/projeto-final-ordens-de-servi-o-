package br.senai.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.OrdemServico;
import br.senai.sistema.service.ClienteService;
import br.senai.sistema.service.OrdemServicoService;
import br.senai.sistema.service.TecnicoService;
import jakarta.validation.Valid;

/**
 * Controller de OrdemServico: recebe as requisições do navegador para /ordens.
 */
@Controller
@RequestMapping("/ordens")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService,
                                   ClienteService clienteService,
                                   TecnicoService tecnicoService) {
        this.ordemServicoService = ordemServicoService;
        this.clienteService = clienteService;
        this.tecnicoService = tecnicoService;
    }

    /** GET /ordens -> mostra a lista. */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ordens", ordemServicoService.listarTodos());
        return "ordens/lista";
    }

    /** GET /ordens/novo -> abre o formulário vazio. */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("ordemServico", new OrdemServico());
        carregarListas(model);
        return "ordens/form";
    }

    /**
     * POST /ordens/salvar -> recebe o formulário (cadastro ou edição).
     * @Valid confere as regras da entidade; os erros ficam em "resultado".
     */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("ordemServico") OrdemServico ordemServico, BindingResult resultado,
                         Model model, RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            carregarListas(model);
            return "ordens/form";   // volta para o formulário mostrando os erros
        }
        ordemServicoService.salvar(ordemServico);
        atributos.addFlashAttribute("mensagem", "Ordem de serviço salva com sucesso.");
        return "redirect:/ordens";
    }

    /** GET /ordens/5/editar -> abre o formulário preenchido com o registro de id 5. */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("ordemServico", ordemServicoService.buscarPorId(id));
        carregarListas(model);
        return "ordens/form";
    }

    /** POST /ordens/5/excluir -> exclui o registro de id 5. */
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        ordemServicoService.excluir(id);
        atributos.addFlashAttribute("mensagem", "Ordem de serviço excluída com sucesso.");
        return "redirect:/ordens";
    }

    /** Coloca no model as listas usadas nas listas de escolha (select) do formulário. */
    private void carregarListas(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("tecnicos", tecnicoService.listarTodos());
    }
}