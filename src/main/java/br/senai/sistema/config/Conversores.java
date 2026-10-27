package br.senai.sistema.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.senai.sistema.model.Cliente;
import br.senai.sistema.model.Tecnico;
import br.senai.sistema.repository.ClienteRepository;
import br.senai.sistema.repository.TecnicoRepository;

/**
 * Conversores: transformam o id que vem do formulário (um texto, por exemplo "3")
 * no objeto correspondente do banco. São usados pelas listas de escolha (select)
 * dos formulários que têm relacionamento.
 */
@Configuration
public class Conversores implements WebMvcConfigurer {

    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;

    public Conversores(ClienteRepository clienteRepository,
                       TecnicoRepository tecnicoRepository) {
        this.clienteRepository = clienteRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // "3" -> cliente de id 3 (texto vazio -> nenhum, e a validação avisa se for obrigatório)
        registry.addConverter(String.class, Cliente.class,
                id -> id.isBlank() ? null : clienteRepository.findById(Long.valueOf(id)).orElse(null));

        // "3" -> técnico de id 3 (texto vazio -> nenhum, e a validação avisa se for obrigatório)
        registry.addConverter(String.class, Tecnico.class,
                id -> id.isBlank() ? null : tecnicoRepository.findById(Long.valueOf(id)).orElse(null));
    }
}