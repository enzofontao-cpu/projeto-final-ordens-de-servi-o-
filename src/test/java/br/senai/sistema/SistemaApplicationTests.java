package br.senai.sistema;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Verifica se a aplicação inicia e se o caminho de acesso negado abre.
 * Execute com:  mvn test
 * Na UC Teste de Sistemas você escreverá testes das regras de negócio.
 */
@SpringBootTest
@AutoConfigureMockMvc
class SistemaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void aplicacaoInicia() {
        // Se o Spring conseguir montar a aplicação, o teste passa.
    }

    @Test
    @WithMockUser
    void paginaDeAcessoNegadoEstaDisponivel() throws Exception {
        mockMvc.perform(get("/acesso-negado"))
                .andExpect(status().isOk())
                .andExpect(view().name("erro/403"));
    }
}
