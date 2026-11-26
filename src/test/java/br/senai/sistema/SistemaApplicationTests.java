package br.senai.sistema;

import br.senai.sistema.model.StatusOrdem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * Verifica se a aplicação inicia e se o caminho de acesso negado abre.
 * Execute com:  mvn test
 * Na UC Teste de Sistemas você escreverá testes das regras de negócio.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.seguranca.ativa=true")
class SistemaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void aplicacaoInicia() {
        // Se o Spring conseguir montar a aplicação, o teste passa.
    }

    @Test
    void statusEnumIncluiCancelada() {
        assertThat(StatusOrdem.values()).contains(StatusOrdem.CANCELADA);
        assertThat(StatusOrdem.CANCELADA.getDescricao()).isEqualTo("Cancelada");
    }

    @Test
    @WithMockUser
    void paginaDeAcessoNegadoEstaDisponivel() throws Exception {
        mockMvc.perform(get("/acesso-negado"))
                .andExpect(status().isOk())
                .andExpect(view().name("erro/403"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void administradorPodePesquisarClientesEOrdens() throws Exception {
        mockMvc.perform(get("/pesquisa").param("q", "marina"))
                .andExpect(status().isOk())
                .andExpect(view().name("pesquisa/resultados"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Marina Costa")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Notebook Dell Inspiron")));
    }

    @Test
    @WithMockUser(roles = "OPERADOR")
    void operadorPodePesquisarTecnicosSemDiferenciarAcentos() throws Exception {
        mockMvc.perform(get("/pesquisa").param("q", "notebooks"))
                .andExpect(status().isOk())
                .andExpect(view().name("pesquisa/resultados"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Carlos Mendes")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Notebooks")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void filtroDeOrdensAbreEExibeSituacaoSelecionada() throws Exception {
        mockMvc.perform(get("/ordens").param("status", "ABERTA"))
                .andExpect(status().isOk())
                .andExpect(view().name("ordens/lista"))
                .andExpect(content().string(containsString("aria-expanded=\"true\"")))
                .andExpect(content().string(containsString("Todas")))
                .andExpect(content().string(containsString("Em andamento")))
                .andExpect(content().string(containsString("Concluída")))
                .andExpect(content().string(not(containsString("Cancelada"))));
    }

    @Test
    @WithMockUser(roles = "OPERADOR")
    void operadorTambemPodeFiltrarOrdensPorSituacao() throws Exception {
        mockMvc.perform(get("/ordens").param("status", "CONCLUIDA"))
                .andExpect(status().isOk())
                .andExpect(view().name("ordens/lista"))
                .andExpect(content().string(containsString("aria-expanded=\"true\"")))
                .andExpect(content().string(containsString("Concluída")));
    }

    @Test
    @WithMockUser(roles = "OPERADOR")
    void operadorNaoVeAcaoDeCancelarOrdem() throws Exception {
        mockMvc.perform(get("/ordens"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("> Cancelar</button>"))));
    }
}
