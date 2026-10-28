package br.senai.sistema.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Cliente;
import br.senai.sistema.model.OrdemServico;
import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.StatusOrdem;
import br.senai.sistema.model.Tecnico;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.ClienteRepository;
import br.senai.sistema.repository.OrdemServicoRepository;
import br.senai.sistema.repository.TecnicoRepository;
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
    private final OrdemServicoRepository ordemServicoRepository;

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         ClienteRepository clienteRepository, TecnicoRepository tecnicoRepository,
                         OrdemServicoRepository ordemServicoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.clienteRepository = clienteRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.ordemServicoRepository = ordemServicoRepository;
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

        // Operação principal: cada registro aponta para registros que já existem (relacionamento).
        // O "%" (resto da divisão) evita erro se existirem menos de 3 registros cadastrados.
        if (ordemServicoRepository.count() == 0) {
            List<Cliente> clientes = clienteRepository.findAll(Sort.by("id"));
            List<Tecnico> tecnicos = tecnicoRepository.findAll(Sort.by("id"));
            if (!clientes.isEmpty() && !tecnicos.isEmpty()) {
                OrdemServico ordemServico1 = new OrdemServico("Notebook Dell Inspiron", "Não liga ao apertar o botão de energia", LocalDate.of(2026, 10, 14), StatusOrdem.ABERTA,
                        clientes.get(0), tecnicos.get(0));
                OrdemServico ordemServico2 = new OrdemServico("Impressora HP LaserJet", "Papel enroscando na saída", LocalDate.of(2026, 10, 15), StatusOrdem.EM_ANDAMENTO,
                        clientes.get(1 % clientes.size()), tecnicos.get(1 % tecnicos.size()));
                ordemServico2.setDiagnostico("Rolete de tração gasto");
                ordemServico2.setValor(new BigDecimal("180.00"));
                OrdemServico ordemServico3 = new OrdemServico("Celular Samsung Galaxy A54", "Tela trincada", LocalDate.of(2026, 10, 16), StatusOrdem.CONCLUIDA,
                        clientes.get(2 % clientes.size()), tecnicos.get(2 % tecnicos.size()));
                ordemServico3.setDiagnostico("Troca do display");
                ordemServico3.setValor(new BigDecimal("450.00"));
                ordemServicoRepository.save(ordemServico1);
                ordemServicoRepository.save(ordemServico2);
                ordemServicoRepository.save(ordemServico3);
            }
        }
    }
}