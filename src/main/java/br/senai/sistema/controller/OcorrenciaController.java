package br.senai.sistema.controller;

import java.security.Principal;
import java.util.Arrays;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.Ocorrencia;
import br.senai.sistema.model.TipoOcorrencia;
import br.senai.sistema.service.OcorrenciaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ocorrencias", ocorrenciaService.listar());
        return "ocorrencias/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model, Principal principal) {
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setLocal("Sistema > Menu principal > Tela inicial");
        model.addAttribute("ocorrencia", ocorrencia);
        carregarFormulario(model, principal);
        return "ocorrencias/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("ocorrencia") Ocorrencia ocorrencia,
                         BindingResult resultado, Model model, Principal principal,
                         RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            carregarFormulario(model, principal);
            return "ocorrencias/form";
        }

        ocorrenciaService.registrar(ocorrencia, usuarioAtual(principal));
        atributos.addFlashAttribute("mensagem", "Registro enviado com sucesso.");
        return "redirect:/ocorrencias";
    }

    private void carregarFormulario(Model model, Principal principal) {
        model.addAttribute("tipos", Arrays.asList(TipoOcorrencia.values()));
        model.addAttribute("usuarioAtual", usuarioAtual(principal));
    }

    private String usuarioAtual(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (principal == null || authentication instanceof AnonymousAuthenticationToken) {
            return "Não autenticado";
        }
        return principal.getName();
    }
}
