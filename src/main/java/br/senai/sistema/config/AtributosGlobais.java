package br.senai.sistema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.senai.sistema.controller.ClienteController;
import br.senai.sistema.controller.InicioController;
import br.senai.sistema.controller.OrdemServicoController;
import br.senai.sistema.controller.TecnicoController;

/**
 * Disponibiliza valores para TODAS as páginas.
 *
 * O nome do sistema vem de application.yml (app: nome:) e aparece
 * no menu e no título das páginas. Altere lá para o nome do SEU sistema.
 */
@ControllerAdvice(assignableTypes = {
        InicioController.class,
        ClienteController.class,
        TecnicoController.class,
        OrdemServicoController.class
})
public class AtributosGlobais {

    @Value("${app.nome:Meu Sistema}")
    private String nomeDoSistema;

    @Value("${app.empresa:}")
    private String nomeDaEmpresa;

    @ModelAttribute("appNome")
    public String appNome() {
        return nomeDoSistema;
    }

    @ModelAttribute("appEmpresa")
    public String appEmpresa() {
        return nomeDaEmpresa;
    }
}