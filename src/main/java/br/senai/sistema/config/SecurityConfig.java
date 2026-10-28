package br.senai.sistema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança (login e permissões).
 *
 * LIGADA desde o Encontro 7 (application.yml -> app: seguranca: ativa: true).
 * Perfis:
 *   ADMIN    -> pode tudo.
 *   OPERADOR -> consulta produtos e categorias e registra movimentações;
 *               não cadastra, não edita e não exclui produtos e categorias.
 * As regras são conferidas DE CIMA PARA BAIXO: vale a primeira que combinar com o endereço.
 */
@Configuration
public class SecurityConfig {

    @Value("${app.seguranca.ativa:false}")
    private boolean ativa;

    @Bean
    public SecurityFilterChain filtroDeSeguranca(HttpSecurity http) throws Exception {

        if (ativa) {
            http.authorizeHttpRequests(auth -> auth
                    // Arquivos públicos e páginas que não exigem login
                    .requestMatchers("/css/**", "/js/**", "/img/**", "/login", "/diagnostico").permitAll()
                    // Console do banco: só o ADMIN
                    .requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN")
                    // Clientes: todo perfil autenticado pode consultar a lista
                    .requestMatchers(HttpMethod.GET, "/clientes").authenticated()
                    // Apenas o ADMIN pode cadastrar, editar ou excluir clientes
                    .requestMatchers("/clientes/**").hasRole("ADMIN")
                    // Técnicos: todo perfil autenticado pode consultar a lista
                    .requestMatchers(HttpMethod.GET, "/tecnicos").authenticated()
                    // Apenas o ADMIN pode cadastrar, editar ou excluir técnicos
                    .requestMatchers("/tecnicos/**").hasRole("ADMIN")
                    // Todo o resto exige estar logado (qualquer perfil)
                    .anyRequest().authenticated());
        } else {
            // Segurança desligada: tudo liberado
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        }

        http
            // Página de login personalizada (templates/login.html)
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll())
            .logout(logout -> logout
                    .logoutSuccessUrl("/login?saiu")
                    .permitAll())
            // O console do H2 usa recursos que precisam destas duas liberações
            .exceptionHandling(exceptions -> exceptions.accessDeniedPage("/acesso-negado"))
            .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    /** Algoritmo usado para criptografar e conferir senhas. */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}