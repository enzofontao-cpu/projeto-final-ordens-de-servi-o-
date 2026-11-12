ORDEX

| | |
|---|---|
| **Aluno(a)** | Enzo Renan Fontão |
| **Turma** |    TEC-N-001788/2026     |
| **Opção escolhida** | Ordens de Serviço  |
| **Versão atual** | 1.0.0 |

---

## 1. Visão geral

### 1.1 Problema
Empresas que realizam manutenção e assistência técnica precisam controlar os clientes, técnicos e ordens de serviço. Quando essas informações são registradas em papel, planilhas ou conversas no WhatsApp, podem ocorrer dificuldades para acompanhar os serviços, consultar informações e verificar o andamento dos atendimentos. O sistema ORDEX busca centralizar esses dados em uma aplicação web, facilitando o gerenciamento das ordens de serviço e o acompanhamento dos atendimentos.

### 1.2 Canvas do projeto

| Bloco | Resposta |
|---|---|
| **Usuários** (quem usa o sistema) | Administradores, operadores e técnicos responsáveis pelos serviços. |
| **Problema** (dor atual) | Dificuldade para organizar clientes, técnicos e serviços, além de acompanhar o andamento das ordens de serviço.|
| **Proposta de valor** (o que melhora com o sistema) | Centralizar as informações, facilitar consultas e permitir o acompanhamento organizado das ordens de serviço. |
| **Funcionalidades principais** | Cadastro e consulta de clientes, cadastro e consulta de técnicos, abertura de ordens de serviço, edição de informações, acompanhamento de status e gerenciamento dos atendimentos. |
| **Informações que o sistema guarda** | Dados dos clientes, dados dos técnicos, equipamentos, defeitos relatados, diagnósticos, valores, datas de abertura e situações das ordens de serviço. |
| **Indicadores** (o que o gestor quer acompanhar) | Quantidade de ordens abertas, em andamento e concluídas; quantidade de atendimentos por técnico; ordens pendentes e valores dos serviços. |
| **Restrições** (prazo, tecnologia, equipe) | Projeto individual · Java 21 · Spring Boot 4 · entrega v1.0 em 11/11 |

---

## 2. Requisitos

### 2.1 Requisitos funcionais (o que o sistema FAZ)

| ID | Requisito | Nível |
|---|---|---|
| RF01 | O sistema deve permitir cadastrar, listar, editar e excluir clientes. | Essencial |
| RF02 | O sistema deve permitir cadastrar, listar, editar e excluir técnicos. | Essencial |
| RF03 | O sistema deve permitir abrir, consultar, editar e excluir ordens de serviço. | Essencial |
| RF04 | O sistema deve permitir associar uma ordem de serviço a um cliente e a um técnico responsável. | Essencial | 
| RF05 | O sistema deve permitir registrar o equipamento, o defeito relatado, o diagnóstico e o valor do serviço. | Essencial |

### 2.2 Requisitos não funcionais (COMO o sistema deve ser)

| ID | Requisito |
|---|---|
| RNF01 | O sistema deve ser acessado pelo navegador, utilizando uma aplicação web. |
| RNF02 | O sistema deve exigir login e senha; as senhas devem ser armazenadas de forma segura, utilizando criptografia apropriada para senhas. |
| RNF03 | O sistema deve ser desenvolvido utilizando Java 21 e Spring Boot 4. |
| RNF04 | O sistema deve utilizar Spring Data JPA para persistência dos dados.|
| RNF05 | O sistema deve utilizar banco de dados H2 durante o desenvolvimento e permitir utilização do MySQL em produção. |

### 2.3 Regras de negócio (as REGRAS do negócio que o sistema precisa respeitar)

| ID | Regra |
|---|---|
| RN01 | Toda ordem de serviço deve estar vinculada a um cliente cadastrado. |
| RN02 | Toda ordem de serviço deve possuir um equipamento e um defeito relatado. |
| RN03 | Toda ordem de serviço deve possuir uma data de abertura. |
| RN04 | O valor de uma ordem de serviço não pode ser negativo. |
| RN05 | Uma ordem de serviço deve possuir uma situação válida: Aberta, Em andamento, Concluída ou Cancelada. |
---

## 3. Histórias de usuário

Formato: **Como** *[papel]*, **quero** *[ação]*, **para** *[benefício]*.

**HU01 —** 
Como administrador, quero cadastrar, consultar, editar e excluir clientes, para manter os dados dos clientes organizados no sistema.

-Critério de aceite: O sistema deve permitir cadastrar um cliente com nome e demais informações disponíveis.

-Critério de aceite: O sistema deve permitir consultar os clientes cadastrados.

-Critério de aceite: O sistema deve permitir editar e excluir clientes, respeitando os vínculos com ordens de serviço.

**HU02 —** Cadastro de técnicos
Como administrador, quero cadastrar e consultar técnicos, para organizar os profissionais responsáveis pelos atendimentos.

Critério de aceite: O sistema deve permitir cadastrar técnicos com nome, especialidade e telefone.

Critério de aceite: O sistema deve permitir consultar os técnicos cadastrados.

Critério de aceite: O sistema deve permitir editar e excluir técnicos, respeitando os vínculos com ordens de serviço.
---

## 4. Modelo de dados

```mermaid
classDiagram
    direction LR

    class Cliente {
        Long id
        String nome
        String telefone
        String email
        String cidade
        LocalDate dataCadastro
    }

    class Tecnico {
        Long id
        String nome
        String especialidade
        String telefone
    }

    class OrdemServico {
        Long id
        String equipamento
        String defeitoRelatado
        String diagnostico
        BigDecimal valor
        String status
        LocalDate dataAbertura
    }

    Cliente "1" --> "*" OrdemServico : solicita
    Tecnico "1" --> "*" OrdemServico : atende
```

---

## 5. Como executar

### No GitHub Codespaces (recomendado)
1. No repositório, clique em **Code → Codespaces** e abra o Codespace existente (ou **Create codespace on main**).
2. No terminal, execute:
   ```bash
   mvn spring-boot:run
   ```
3. Aguarde a mensagem `Started SistemaApplication` no terminal.
4. Abra a aba **Portas** (ao lado do Terminal) e, na linha da porta **8080**, clique no ícone de globo (**Abrir no navegador**).
5. Entre com um dos usuários da seção **Usuários de teste**.

### No computador (ferramentas instaladas)
Requisitos: JDK 21 e Maven (ou uma IDE com suporte a Java, como IntelliJ IDEA ou VS Code com o *Extension Pack for Java*).
Na pasta do projeto, execute `mvn spring-boot:run` e acesse `http://localhost:8080`.

### Banco de dados de desenvolvimento
- O banco H2 fica na pasta `dados/` (não vai para o GitHub). Na primeira execução, o sistema cria as tabelas e os dados de exemplo.
- Para recomeçar com os dados de exemplo: pare o sistema e apague a pasta (`rm -rf dados`).
- Console do banco: endereço `/h2-console` (só para o perfil ADMIN).


### No GitHub Codespaces (recomendado)
1. No repositório, clique em **Code → Codespaces → Create codespace on main** (ou abra o Codespace existente).
2. Aguarde a preparação do ambiente.
3. No terminal, execute:
   ```bash
   mvn spring-boot:run
   ```
4. Quando aparecer o aviso da porta **8080**, clique em **Abrir no navegador**.

### No computador (ferramentas instaladas)
Requisitos: JDK 21 e uma IDE (IntelliJ IDEA ou VS Code com o *Extension Pack for Java*).
Abra o projeto na IDE e execute a classe `SistemaApplication`. Acesse `http://localhost:8080`.

### Acesso
| Usuário | Senha | Perfil |
|---|---|---|
| admin | admin123 | Administrador |
| operador | operador123 | Operador |

*(o login passa a ser exigido a partir do Encontro 7)*

---

## 6. Tecnologias
Java 21 · Spring Boot 4 · Spring MVC · Thymeleaf · Bootstrap 5 · Spring Data JPA · H2 (desenvolvimento) · MySQL (produção) · Spring Security · Git/GitHub

## 7. Uso de inteligência artificial
A inteligência artificial foi utilizada como ferramenta de apoio ao desenvolvimento do projeto,como correção de bugs como nos logins dos usuários de teste, correção de pequenos mals funcionamentos

## 8. Histórico de versões
| Versão | Data | Descrição |
|---|---|---|
| 0.1.0 | | Projeto inicial criado a partir do repositório modelo |
| 1.0.0 | 11/11/2026 | Primeira versão estável |

## Usuários de teste

| Usuário | Senha | Perfil | O que pode fazer |
|---|---|---|---|
| admin | admin123 | Administrador | Tudo |
| operador | operador123 | Operador | Consulta clientes e técnicos, abre ordens de serviço e muda a situação delas |

## 9. Funcionalidades e telas

### Funcionalidades
- Cadastro de clientes e técnicos.
- Abertura e acompanhamento das ordens de serviço.
- Situação de cada ordem: aberta, em andamento ou concluída.
- Painel com os indicadores do atendimento.
- Login com dois perfis: o Administrador pode tudo; o Operador consulta clientes e técnicos, abre ordens de serviço e muda a situação delas.

### Telas

**Painel com os indicadores do atendimento**

![Painel com os indicadores do atendimento](docs/painel.png)

**Ordens de serviço e suas situações**

![Ordens de serviço e suas situações](docs/ordens.png)

**Regra de negócio: concluir sem diagnóstico e valor**

![Regra de negócio: concluir sem diagnóstico e valor](docs/regra.png)

**Tela de login**

![Tela de login](docs/login.png)

## 10. Checklist do nível essencial

- [x] O sistema sobe sem erros seguindo a seção 5 (Como executar)
- [x] Cadastro completo de clientes (cadastrar, listar, editar e excluir), com validação
- [x] Cadastro completo de técnicos, com validação
- [x] Ordens de serviço ligadas a clientes e técnicos, com lista de escolha no formulário
- [x] Regra de negócio 1: Não concluir uma ordem de serviço sem diagnóstico e valor.
- [x] Regra de negócio 2: Não alterar nem excluir uma ordem de serviço concluída.
- [x] Login com 2 perfis (admin e operador), senhas criptografadas e acesso conforme o perfil
- [x] Painel com os indicadores na página inicial
- [x] Repositório no GitHub com commits frequentes
- [x] README com a visão do projeto, como executar, usuários de teste, funcionalidades e telas
- [x] Quadro Kanban atualizado
