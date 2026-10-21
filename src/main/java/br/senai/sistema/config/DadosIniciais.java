package br.senai.sistema.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Tecnico;
import br.senai.sistema.model.Cliente;
import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.TecnicoRepository;
import br.senai.sistema.repository.ClienteRepository;
import br.senai.sistema.repository.UsuarioRepository;

/**
 * Carga inicial de dados: executa uma vez sempre que a aplicação sobe.
 * Cada bloco só insere os dados se a tabela ainda estiver vazia (por isso não duplica).
 */
@Component
public class DadosIniciais implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         ClienteRepository clienteRepository, TecnicoRepository tecnicoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.clienteRepository = clienteRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Administrador", "admin",
                    passwordEncoder.encode("admin123"), Perfil.ADMIN));
            usuarioRepository.save(new Usuario("Operador", "operador",
                    passwordEncoder.encode("operador123"), Perfil.OPERADOR));
        }

        if (clienteRepository.count() == 0) {
            Cliente cliente1 = new Cliente("Marina Costa", "(43) 99999-1001", "marina@email.com", "Londrina", LocalDate.of(2026, 8, 12));
            Cliente cliente2 = new Cliente("Oficina Silva Ltda.", "(43) 3333-2002", "contato@oficinasilva.com", "Cambé", LocalDate.of(2026, 9, 3));
            Cliente cliente3 = new Cliente("Pedro Henrique Alves", "(43) 98888-3003", "pedro@email.com", "Ibiporã", LocalDate.of(2026, 10, 1));
            clienteRepository.save(cliente1);
            clienteRepository.save(cliente2);
            clienteRepository.save(cliente3);
        }

        if (tecnicoRepository.count() == 0) {
            Tecnico tecnico1 = new Tecnico("Carlos Mendes", "Notebooks", "(43) 99111-2233");
            Tecnico tecnico2 = new Tecnico("Juliana Prado", "Impressoras", "(43) 99222-3344");
            Tecnico tecnico3 = new Tecnico("Rafael Souza", "Celulares", "(43) 99333-4455");
            tecnicoRepository.save(tecnico1);
            tecnicoRepository.save(tecnico2);
            tecnicoRepository.save(tecnico3);
        }
    }
}