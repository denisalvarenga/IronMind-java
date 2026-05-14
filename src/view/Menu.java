package view;

import controller.AlunoController;
import controller.AulaController;
import controller.FrequenciaController;
import controller.InstrutorController;
import controller.PlanoController;

import dao.RelatorioDAO;
import model.Aluno;
import model.Aula;
import model.Frequencia;
import model.Instrutor;
import model.Plano;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela interface de linha de comando do sistema IronMind.
 *
 * Gerencia os menus de navegação e coleta de dados do usuário,
 * delegando todas as operações para os Controllers correspondentes.
 */
public class Menu {

    private final Scanner sc = new Scanner(System.in);

    private final AlunoController alunoController;
    private final PlanoController planoController;
    private final AulaController aulaController;
    private final InstrutorController instrutorController;
    private final FrequenciaController frequenciaController;
    private final RelatorioDAO relatorioDAO;

    /**
     * Construtor padrão. Inicializa todos os controllers.
     */
    public Menu() {
        this.alunoController = new AlunoController();
        this.planoController = new PlanoController();
        this.aulaController = new AulaController();
        this.instrutorController = new InstrutorController();
        this.frequenciaController = new FrequenciaController();
        this.relatorioDAO = new RelatorioDAO();
    }

    /**
     * Inicia o loop principal do menu.
     */
    public void iniciar() {

        int op;

        do {
            limparTela();
            exibirMenuPrincipal();

            op = lerInt();

            limparTela();
            executarOpcaoPrincipal(op);

            if (op != 0) {
                pausar();
            }

        } while (op != 0);
    }

    /**
     * Exibe o menu principal.
     */
    private void exibirMenuPrincipal() {
        System.out.println("=== IRON MIND ===");
        System.out.println("1 - Alunos");
        System.out.println("2 - Instrutores");
        System.out.println("3 - Planos");
        System.out.println("4 - Aulas");
        System.out.println("5 - Inscrição em aula");
        System.out.println("6 - Cancelar inscrição");
        System.out.println("7 - Registrar frequência");
        System.out.println("8 - Relatório de frequência");
        System.out.println("9 - Relatório de ocupação");
        System.out.println("10 - Resumo completo do aluno");
        System.out.println("0 - Sair");
    }

    /**
     * Executa a opção escolhida no menu principal.
     *
     * @param op opção selecionada
     */
    private void executarOpcaoPrincipal(int op) {
        switch (op) {
            case 1  -> menuAluno();
            case 2  -> menuInstrutor();
            case 3  -> menuPlano();
            case 4  -> menuAula();
            case 5  -> inscreverAluno();
            case 6  -> cancelarInscricao();
            case 7  -> registrarFrequencia();
            case 8  -> relatorioFrequencia();
            case 9  -> relatorioOcupacao();
            case 10 -> resumoAluno();
            case 0  -> System.out.println("Encerrando sistema...");
            default -> System.out.println("Opção inválida.");
        }
    }

    // ================= ALUNO =================

    /**
     * Submenu de gerenciamento de alunos (CRUD completo).
     */
    private void menuAluno() {
        System.out.println("=== ALUNOS ===");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");
        System.out.println("3 - Editar");
        System.out.println("4 - Remover");

        int op = lerInt();

        switch (op) {
            case 1 -> cadastrarAluno();
            case 2 -> alunoController.listarAlunos();
            case 3 -> editarAluno();
            case 4 -> removerAluno();
            default -> System.out.println("Opção inválida.");
        }
    }

    /**
     * Coleta dados e cadastra um novo aluno.
     */
    private void cadastrarAluno() {

        List<Plano> planos = planoController.consultarTodos();

        if (planos.isEmpty()) {
            System.out.println("Cadastre um plano primeiro.");
            return;
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Data de nascimento (AAAA-MM-DD): ");
        LocalDate dataNasc = LocalDate.parse(sc.nextLine().trim());

        System.out.print("Data de matrícula (AAAA-MM-DD) [Enter = hoje]: ");
        String entradaMatricula = sc.nextLine().trim();
        LocalDate dataMatricula = entradaMatricula.isEmpty()
                ? LocalDate.now()
                : LocalDate.parse(entradaMatricula);

        System.out.println("Selecione o plano:");
        Plano plano = selecionarPlano(planos);

        if (plano == null) {
            System.out.println("Plano inválido.");
            return;
        }

        Aluno aluno = new Aluno(nome, cpf, telefone, dataNasc, email, dataMatricula, plano);
        alunoController.cadastrarAluno(aluno);
    }

    /**
     * Edita os dados de um aluno existente.
     */
    private void editarAluno() {
        List<Aluno> alunos = alunoController.consultarTodos();
        if (alunos.isEmpty()) { System.out.println("Nenhum aluno cadastrado."); return; }

        System.out.println("Selecione o aluno:");
        Aluno aluno = selecionarAluno(alunos);
        if (aluno == null) { System.out.println("Aluno inválido."); return; }

        System.out.println("--- Novos dados para: " + aluno.getNome() + " ---");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        List<Plano> planos = planoController.consultarTodos();
        System.out.println("Selecione o novo plano:");
        Plano plano = selecionarPlano(planos);

        Aluno novoAluno = new Aluno(
                nome, cpf, telefone,
                aluno.getDataNascimento(), email,
                aluno.getDataMatricula(), plano
        );

        alunoController.atualizarAluno(aluno.getId(), novoAluno);
    }

    /**
     * Remove um aluno selecionado.
     */
    private void removerAluno() {
        List<Aluno> alunos = alunoController.consultarTodos();
        if (alunos.isEmpty()) { System.out.println("Nenhum aluno cadastrado."); return; }

        System.out.println("Selecione o aluno a remover:");
        Aluno aluno = selecionarAluno(alunos);
        if (aluno == null) { System.out.println("Aluno inválido."); return; }

        alunoController.removerAluno(aluno.getId());
    }

    // ================= INSTRUTOR =================

    /**
     * Submenu de gerenciamento de instrutores (CRUD completo).
     */
    private void menuInstrutor() {
        System.out.println("=== INSTRUTORES ===");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");
        System.out.println("3 - Editar");
        System.out.println("4 - Remover");

        int op = lerInt();

        switch (op) {
            case 1 -> cadastrarInstrutor();
            case 2 -> listarInstrutores();
            case 3 -> editarInstrutor();
            case 4 -> removerInstrutor();
            default -> System.out.println("Opção inválida.");
        }
    }

    /**
     * Coleta dados e cadastra um novo instrutor.
     */
    private void cadastrarInstrutor() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();

        System.out.print("Horário de trabalho: ");
        String horario = sc.nextLine();

        Instrutor instrutor = new Instrutor(nome, cpf, telefone, especialidade, horario);
        instrutorController.cadastrarInstrutor(instrutor);
    }

    /**
     * Lista todos os instrutores.
     */
    private void listarInstrutores() {
        List<Instrutor> instrutores = instrutorController.listarInstrutores();
        if (instrutores.isEmpty()) {
            System.out.println("Nenhum instrutor cadastrado.");
            return;
        }
        System.out.println("===== LISTA DE INSTRUTORES =====");
        for (Instrutor i : instrutores) {
            System.out.println("ID: " + i.getId() + " | " + i.exibirResumo()
                    + " | Especialidade: " + i.getEspecialidade());
            System.out.println("----------------------------");
        }
    }

    /**
     * Edita os dados de um instrutor existente.
     */
    private void editarInstrutor() {
        List<Instrutor> instrutores = instrutorController.listarInstrutores();
        if (instrutores.isEmpty()) { System.out.println("Nenhum instrutor cadastrado."); return; }

        System.out.println("Selecione o instrutor:");
        Instrutor instrutor = selecionarInstrutor(instrutores);
        if (instrutor == null) { System.out.println("Inválido."); return; }

        System.out.println("--- Novos dados para: " + instrutor.getNome() + " ---");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();

        System.out.print("Horário de trabalho: ");
        String horario = sc.nextLine();

        Instrutor novo = new Instrutor(nome, cpf, telefone, especialidade, horario);
        instrutorController.atualizarInstrutor(instrutor.getId(), novo);
    }

    /**
     * Remove um instrutor selecionado.
     */
    private void removerInstrutor() {
        List<Instrutor> instrutores = instrutorController.listarInstrutores();
        if (instrutores.isEmpty()) { System.out.println("Nenhum instrutor cadastrado."); return; }

        System.out.println("Selecione o instrutor a remover:");
        Instrutor instrutor = selecionarInstrutor(instrutores);
        if (instrutor == null) { System.out.println("Inválido."); return; }

        instrutorController.removerInstrutor(instrutor.getId());
    }

    // ================= PLANO =================

    /**
     * Submenu de gerenciamento de planos (CRUD completo).
     */
    private void menuPlano() {
        System.out.println("=== PLANOS ===");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");
        System.out.println("3 - Editar");
        System.out.println("4 - Remover");

        int op = lerInt();

        switch (op) {
            case 1 -> cadastrarPlano();
            case 2 -> planoController.listar();
            case 3 -> editarPlano();
            case 4 -> removerPlano();
            default -> System.out.println("Opção inválida.");
        }
    }

    /**
     * Coleta dados e cadastra um novo plano.
     */
    private void cadastrarPlano() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        System.out.print("Valor mensal (R$): ");
        double valor = lerDouble();

        System.out.print("Benefícios: ");
        String beneficios = sc.nextLine();

        System.out.print("Duração (meses): ");
        int duracao = lerInt();

        Plano plano = new Plano(nome, descricao, valor, beneficios, duracao);
        planoController.criar(plano);
    }

    /**
     * Edita os dados de um plano existente.
     */
    private void editarPlano() {
        List<Plano> planos = planoController.consultarTodos();
        if (planos.isEmpty()) { System.out.println("Nenhum plano cadastrado."); return; }

        System.out.println("Selecione o plano:");
        Plano plano = selecionarPlano(planos);
        if (plano == null) { System.out.println("Inválido."); return; }

        System.out.println("--- Novos dados para: " + plano.getNome() + " ---");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        System.out.print("Valor mensal (R$): ");
        double valor = lerDouble();

        System.out.print("Benefícios: ");
        String beneficios = sc.nextLine();

        System.out.print("Duração (meses): ");
        int duracao = lerInt();

        Plano novo = new Plano(nome, descricao, valor, beneficios, duracao);
        planoController.atualizar(plano.getId(), novo);
    }

    /**
     * Remove um plano selecionado.
     */
    private void removerPlano() {
        List<Plano> planos = planoController.consultarTodos();
        if (planos.isEmpty()) { System.out.println("Nenhum plano cadastrado."); return; }

        System.out.println("Selecione o plano a remover:");
        Plano plano = selecionarPlano(planos);
        if (plano == null) { System.out.println("Inválido."); return; }

        planoController.remover(plano.getId());
    }

    // ================= AULA =================

    /**
     * Submenu de gerenciamento de aulas (CRUD completo).
     */
    private void menuAula() {
        System.out.println("=== AULAS ===");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");
        System.out.println("3 - Editar");
        System.out.println("4 - Remover");

        int op = lerInt();

        switch (op) {
            case 1 -> cadastrarAula();
            case 2 -> aulaController.listar();
            case 3 -> editarAula();
            case 4 -> removerAula();
            default -> System.out.println("Opção inválida.");
        }
    }

    /**
     * Coleta dados e cadastra uma nova aula.
     */
    private void cadastrarAula() {

        List<Instrutor> instrutores = instrutorController.listarInstrutores();

        if (instrutores.isEmpty()) {
            System.out.println("Cadastre um instrutor primeiro.");
            return;
        }

        System.out.print("Nome da aula: ");
        String nome = sc.nextLine();

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        System.out.print("Duração (minutos): ");
        int duracao = lerInt();

        System.out.print("Capacidade: ");
        int capacidade = lerInt();

        System.out.print("Horário (AAAA-MM-DDTHH:MM, ex: 2026-05-10T08:00): ");
        String horarioStr = sc.nextLine().trim();
        LocalDateTime horario = horarioStr.isEmpty()
                ? LocalDateTime.now().plusHours(1)
                : LocalDateTime.parse(horarioStr);

        System.out.println("Selecione o instrutor:");
        Instrutor instrutor = selecionarInstrutor(instrutores);

        if (instrutor == null) {
            System.out.println("Instrutor inválido.");
            return;
        }

        Aula aula = new Aula(nome, descricao, duracao, horario, capacidade, instrutor);
        aulaController.criar(aula);
    }

    /**
     * Edita os dados de uma aula existente.
     */
    private void editarAula() {
        List<Aula> aulas = aulaController.consultarTodos();
        if (aulas.isEmpty()) { System.out.println("Nenhuma aula cadastrada."); return; }

        System.out.println("Selecione a aula:");
        Aula aula = selecionarAula(aulas);
        if (aula == null) { System.out.println("Inválido."); return; }

        System.out.println("--- Novos dados para: " + aula.getNome() + " ---");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        System.out.print("Duração (minutos): ");
        int duracao = lerInt();

        System.out.print("Capacidade: ");
        int capacidade = lerInt();

        System.out.print("Horário (AAAA-MM-DDTHH:MM): ");
        String horarioStr = sc.nextLine().trim();
        LocalDateTime horario = horarioStr.isEmpty()
                ? aula.getHorario()
                : LocalDateTime.parse(horarioStr);

        List<Instrutor> instrutores = instrutorController.listarInstrutores();
        System.out.println("Selecione o instrutor:");
        Instrutor instrutor = selecionarInstrutor(instrutores);

        Aula nova = new Aula(nome, descricao, duracao, horario, capacidade, instrutor);
        aulaController.atualizar(aula.getId(), nova);
    }

    /**
     * Remove uma aula selecionada.
     */
    private void removerAula() {
        List<Aula> aulas = aulaController.consultarTodos();
        if (aulas.isEmpty()) { System.out.println("Nenhuma aula cadastrada."); return; }

        System.out.println("Selecione a aula a remover:");
        Aula aula = selecionarAula(aulas);
        if (aula == null) { System.out.println("Inválido."); return; }

        aulaController.remover(aula.getId());
    }

    // ================= INSCRIÇÃO =================

    /**
     * Inscreve um aluno em uma aula com todas as validações de negócio.
     */
    private void inscreverAluno() {

        List<Aluno> alunos = alunoController.consultarTodos();
        List<Aula> aulas = aulaController.consultarTodos();

        if (alunos.isEmpty() || aulas.isEmpty()) {
            System.out.println("Cadastre aluno e aula primeiro.");
            return;
        }

        System.out.println("Selecione o aluno:");
        Aluno aluno = selecionarAluno(alunos);

        System.out.println("Selecione a aula:");
        Aula aula = selecionarAula(aulas);

        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return;
        }

        aulaController.inscreverAluno(aluno, aula);
    }

    // ================= CANCELAR =================

    /**
     * Cancela a inscrição de um aluno em uma aula.
     */
    private void cancelarInscricao() {

        List<Aluno> alunos = alunoController.consultarTodos();
        List<Aula> aulas = aulaController.consultarTodos();

        if (alunos.isEmpty() || aulas.isEmpty()) {
            System.out.println("Cadastre aluno e aula primeiro.");
            return;
        }

        System.out.println("Selecione o aluno:");
        Aluno aluno = selecionarAluno(alunos);

        System.out.println("Selecione a aula:");
        Aula aula = selecionarAula(aulas);

        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return;
        }

        aulaController.cancelarInscricao(aluno, aula);
    }

    // ================= FREQUÊNCIA =================

    /**
     * Registra a entrada de um aluno na academia.
     */
    private void registrarFrequencia() {

        List<Aluno> alunos = alunoController.consultarTodos();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("Selecione o aluno:");
        Aluno aluno = selecionarAluno(alunos);

        if (aluno == null) {
            System.out.println("Aluno inválido.");
            return;
        }

        Frequencia frequencia = new Frequencia(aluno, LocalDateTime.now());
        boolean sucesso = frequenciaController.registrar(frequencia);

        if (sucesso) {
            System.out.println("Frequência registrada com sucesso.");
        } else {
            System.out.println("Falha ao registrar frequência.");
        }
    }

    /**
     * Exibe relatório de frequência de todos os alunos.
     */
    private void relatorioFrequencia() {

        List<String> frequencias = relatorioDAO.frequenciasComAluno();

        System.out.println("===== RELATÓRIO DE FREQUÊNCIA =====");

        if (frequencias.isEmpty()) {
            System.out.println("Nenhuma frequência registrada.");
            return;
        }

        for (String linha : frequencias) {
            System.out.println(linha);
        }
    }

    /**
     * Exibe relatório de ocupação de todas as aulas.
     */
    private void relatorioOcupacao() {
        aulaController.relatorioOcupacao();
    }

    /**
     * Exibe o resumo completo de um aluno (visitas, aulas, plano).
     */
    private void resumoAluno() {
        List<Aluno> alunos = alunoController.consultarTodos();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("Selecione o aluno:");
        Aluno aluno = selecionarAluno(alunos);

        if (aluno == null) {
            System.out.println("Aluno inválido.");
            return;
        }

        System.out.println("===== RESUMO DO ALUNO =====");
        System.out.println(relatorioDAO.resumoAluno(aluno.getId()));
    }

    // ===== UTILITÁRIOS =====

    /**
     * Limpa a tela do terminal.
     */
    private void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Erro ao limpar tela.");
        }
    }

    /**
     * Aguarda o usuário pressionar ENTER para continuar.
     */
    private void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        sc.nextLine();
    }

    /**
     * Lê um inteiro do terminal com validação de entrada.
     *
     * @return inteiro lido
     */
    private int lerInt() {
        while (!sc.hasNextInt()) {
            System.out.println("Digite um número válido.");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    /**
     * Lê um número decimal do terminal com validação de entrada.
     *
     * @return double lido
     */
    private double lerDouble() {
        while (!sc.hasNextDouble()) {
            System.out.println("Digite um valor numérico válido (use vírgula ou ponto).");
            sc.next();
        }
        double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }

    /**
     * Exibe lista de planos numerada e retorna o selecionado.
     *
     * @param planos lista de planos disponíveis
     * @return plano selecionado ou null se inválido
     */
    private Plano selecionarPlano(List<Plano> planos) {
        for (int i = 0; i < planos.size(); i++) {
            System.out.println(i + " - " + planos.get(i).getNome()
                    + " (R$ " + planos.get(i).getValorMensal()
                    + " / " + planos.get(i).getDuracaoMeses() + " meses)");
        }
        int indice = lerInt();
        return (indice >= 0 && indice < planos.size()) ? planos.get(indice) : null;
    }

    /**
     * Exibe lista de instrutores numerada e retorna o selecionado.
     *
     * @param instrutores lista de instrutores disponíveis
     * @return instrutor selecionado ou null se inválido
     */
    private Instrutor selecionarInstrutor(List<Instrutor> instrutores) {
        for (int i = 0; i < instrutores.size(); i++) {
            System.out.println(i + " - " + instrutores.get(i).getNome()
                    + " (" + instrutores.get(i).getEspecialidade() + ")");
        }
        int indice = lerInt();
        return (indice >= 0 && indice < instrutores.size()) ? instrutores.get(indice) : null;
    }

    /**
     * Exibe lista de alunos numerada e retorna o selecionado.
     *
     * @param alunos lista de alunos disponíveis
     * @return aluno selecionado ou null se inválido
     */
    private Aluno selecionarAluno(List<Aluno> alunos) {
        for (int i = 0; i < alunos.size(); i++) {
            System.out.println(i + " - " + alunos.get(i).getNome());
        }
        int indice = lerInt();
        return (indice >= 0 && indice < alunos.size()) ? alunos.get(indice) : null;
    }

    /**
     * Exibe lista de aulas numerada e retorna a selecionada.
     *
     * @param aulas lista de aulas disponíveis
     * @return aula selecionada ou null se inválida
     */
    private Aula selecionarAula(List<Aula> aulas) {
        for (int i = 0; i < aulas.size(); i++) {
            System.out.println(i + " - " + aulas.get(i).getNome()
                    + " (" + aulas.get(i).getHorario() + ")");
        }
        int indice = lerInt();
        return (indice >= 0 && indice < aulas.size()) ? aulas.get(indice) : null;
    }
}
