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

import br.senai.sistema.model.Tecnico;
import br.senai.sistema.service.TecnicoService;
import jakarta.validation.Valid;

/**
 * Controller de Tecnico: recebe as requisições do navegador para /tecnicos.
 */
@Controller
@RequestMapping("/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    /** GET /tecnicos -> mostra a lista. */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tecnicos", tecnicoService.listarTodos());
        return "tecnicos/lista";
    }

    /** GET /tecnicos/novo -> abre o formulário vazio. */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("tecnico", new Tecnico());
        return "tecnicos/form";
    }

    /**
     * POST /tecnicos/salvar -> recebe o formulário (cadastro ou edição).
     * @Valid confere as regras da entidade; os erros ficam em "resultado".
     */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("tecnico") Tecnico tecnico, BindingResult resultado,
                         RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            return "tecnicos/form";   // volta para o formulário mostrando os erros
        }
        tecnicoService.salvar(tecnico);
        atributos.addFlashAttribute("mensagem", "Técnico salvo com sucesso.");
        return "redirect:/tecnicos";
    }

    /** GET /tecnicos/5/editar -> abre o formulário preenchido com o registro de id 5. */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("tecnico", tecnicoService.buscarPorId(id));
        return "tecnicos/form";
    }

    /** POST /tecnicos/5/excluir -> exclui o registro de id 5. */
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        tecnicoService.excluir(id);
        atributos.addFlashAttribute("mensagem", "Técnico excluído com sucesso.");
        return "redirect:/tecnicos";
    }
}
