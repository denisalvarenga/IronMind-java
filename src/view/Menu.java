package view;

import controller.AlunoController;
import controller.AulaController;
import controller.FrequenciaController;
import controller.InstrutorController;
import controller.PlanoController;

import model.Aluno;
import model.Aula;
import model.Frequencia;
import model.Instrutor;
import model.Plano;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner sc = new Scanner(System.in);

    private final AlunoController alunoController;
    private final PlanoController planoController;
    private final AulaController aulaController;
    private final InstrutorController instrutorController;
    private final FrequenciaController frequenciaController;

    public Menu() {
        this.alunoController = new AlunoController();
        this.planoController = new PlanoController();
        this.aulaController = new AulaController();
        this.instrutorController = new InstrutorController();
        this.frequenciaController = new FrequenciaController();
    }

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
        System.out.println("9 - Relatório de ocupação"); // NOVO
        System.out.println("0 - Sair");
    }

    private void executarOpcaoPrincipal(int op) {
        switch (op) {
            case 1 -> menuAluno();
            case 2 -> menuInstrutor();
            case 3 -> menuPlano();
            case 4 -> menuAula();
            case 5 -> inscreverAluno();
            case 6 -> cancelarInscricao();
            case 7 -> registrarFrequencia();
            case 8 -> relatorioFrequencia();
            case 9 -> relatorioOcupacao(); // NOVO
            case 0 -> System.out.println("Encerrando sistema...");
            default -> System.out.println("Opção inválida.");
        }
    }

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

    private void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        sc.nextLine();
    }

    private int lerInt() {
        while (!sc.hasNextInt()) {
            System.out.println("Digite um número válido.");
            sc.next();
        }

        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    // ================= ALUNO =================

    private void menuAluno() {
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");

        int op = lerInt();

        switch (op) {
            case 1 -> cadastrarAluno();
            case 2 -> alunoController.listarAlunos();
            default -> System.out.println("Opção inválida.");
        }
    }

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
        LocalDate nascimento = LocalDate.parse(sc.nextLine());

        Plano plano = selecionarPlano(planos);

        if (plano == null) {
            System.out.println("Plano inválido.");
            return;
        }

        Aluno aluno = new Aluno(
                nome,
                cpf,
                telefone,
                nascimento,
                email,
                LocalDate.now(),
                plano
        );

        alunoController.cadastrarAluno(aluno);
    }

    // ================= INSTRUTOR =================

    private void menuInstrutor() {
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");

        int op = lerInt();

        switch (op) {
            case 1 -> cadastrarInstrutor();
            case 2 -> listarInstrutores();
            default -> System.out.println("Opção inválida.");
        }
    }

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

        Instrutor instrutor = new Instrutor(
                nome,
                cpf,
                telefone,
                especialidade,
                horario
        );

        instrutorController.cadastrarInstrutor(instrutor);
    }

    private void listarInstrutores() {

        List<Instrutor> instrutores = instrutorController.listarInstrutores();

        if (instrutores.isEmpty()) {
            System.out.println("Nenhum instrutor cadastrado.");
            return;
        }

        for (Instrutor instrutor : instrutores) {
            System.out.println(
                    instrutor.getNome()
                            + " - "
                            + instrutor.getEspecialidade()
            );
        }
    }

    // ================= PLANO =================

    private void menuPlano() {
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");

        int op = lerInt();

        switch (op) {
            case 1 -> cadastrarPlano();
            case 2 -> planoController.listar();
            default -> System.out.println("Opção inválida.");
        }
    }

    private void cadastrarPlano() {

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        System.out.print("Valor mensal: ");
        double valor = Double.parseDouble(sc.nextLine());

        System.out.print("Benefícios: ");
        String beneficios = sc.nextLine();

        System.out.print("Duração em meses: ");
        int duracao = lerInt();

        Plano plano = new Plano(
                nome,
                descricao,
                valor,
                beneficios,
                duracao
        );

        planoController.criar(plano);
    }

    // ================= AULA =================

    private void menuAula() {
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");

        int op = lerInt();

        switch (op) {
            case 1 -> cadastrarAula();
            case 2 -> aulaController.listar();
            default -> System.out.println("Opção inválida.");
        }
    }

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

        Instrutor instrutor = selecionarInstrutor(instrutores);

        if (instrutor == null) {
            System.out.println("Instrutor inválido.");
            return;
        }

        Aula aula = new Aula(
                nome,
                descricao,
                duracao,
                LocalDateTime.now().plusHours(1),
                capacidade,
                instrutor
        );

        aulaController.criar(aula);
    }

    // ================= INSCRIÇÃO =================

    private void inscreverAluno() {

        List<Aluno> alunos = alunoController.consultarTodos();
        List<Aula> aulas = aulaController.consultarTodos();

        if (alunos.isEmpty() || aulas.isEmpty()) {
            System.out.println("Cadastre aluno e aula primeiro.");
            return;
        }

        Aluno aluno = selecionarAluno(alunos);
        Aula aula = selecionarAula(aulas);

        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return;
        }

        aulaController.inscreverAluno(aluno, aula);
    }

    // ================= CANCELAR =================

    private void cancelarInscricao() {

        List<Aluno> alunos = alunoController.consultarTodos();
        List<Aula> aulas = aulaController.consultarTodos();

        if (alunos.isEmpty() || aulas.isEmpty()) {
            System.out.println("Cadastre aluno e aula primeiro.");
            return;
        }

        Aluno aluno = selecionarAluno(alunos);
        Aula aula = selecionarAula(aulas);

        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return;
        }

        aulaController.cancelarInscricao(aluno, aula);
    }

    // ================= FREQUÊNCIA =================

    private void registrarFrequencia() {

        List<Aluno> alunos = alunoController.consultarTodos();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        Aluno aluno = selecionarAluno(alunos);

        if (aluno == null) {
            System.out.println("Aluno inválido.");
            return;
        }

        Frequencia frequencia = new Frequencia(
                aluno,
                LocalDateTime.now()
        );

        boolean sucesso = frequenciaController.registrar(frequencia);

        if (sucesso) {
            System.out.println("Frequência registrada com sucesso.");
        } else {
            System.out.println("Falha ao registrar frequência.");
        }
    }

    private void relatorioFrequencia() {

        List<Aluno> alunos = alunoController.consultarTodos();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("===== RELATÓRIO DE FREQUÊNCIA =====");

        for (Aluno aluno : alunos) {
            System.out.println(aluno.exibirResumo());
            System.out.println("-----------------------------------");
        }
    }

    // ================= RELATÓRIO DE OCUPAÇÃO =================

    private void relatorioOcupacao() {
        aulaController.relatorioOcupacao();
    }

    // ===== AUXILIARES =====

    private Plano selecionarPlano(List<Plano> planos) {
        for (int i = 0; i < planos.size(); i++) {
            System.out.println(i + " - " + planos.get(i).getNome());
        }
        int indice = lerInt();
        return (indice >= 0 && indice < planos.size()) ? planos.get(indice) : null;
    }

    private Instrutor selecionarInstrutor(List<Instrutor> instrutores) {
        for (int i = 0; i < instrutores.size(); i++) {
            System.out.println(i + " - " + instrutores.get(i).getNome());
        }
        int indice = lerInt();
        return (indice >= 0 && indice < instrutores.size()) ? instrutores.get(indice) : null;
    }

    private Aluno selecionarAluno(List<Aluno> alunos) {
        for (int i = 0; i < alunos.size(); i++) {
            System.out.println(i + " - " + alunos.get(i).getNome());
        }
        int indice = lerInt();
        return (indice >= 0 && indice < alunos.size()) ? alunos.get(indice) : null;
    }

    private Aula selecionarAula(List<Aula> aulas) {
        for (int i = 0; i < aulas.size(); i++) {
            System.out.println(i + " - " + aulas.get(i).getNome());
        }
        int indice = lerInt();
        return (indice >= 0 && indice < aulas.size()) ? aulas.get(indice) : null;
    }
}