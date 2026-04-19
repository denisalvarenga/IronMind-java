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

            System.out.println("=== IRON MIND ===");
            System.out.println("1 - Alunos");
            System.out.println("2 - Instrutores");
            System.out.println("3 - Planos");
            System.out.println("4 - Aulas");
            System.out.println("5 - Inscrição em aula");
            System.out.println("6 - Cancelar inscrição");
            System.out.println("7 - Registrar frequência");
            System.out.println("8 - Relatório de frequência");
            System.out.println("0 - Sair");

            op = lerInt();

            limparTela();

            switch (op) {
                case 1 -> menuAluno();
                case 2 -> menuInstrutor();
                case 3 -> menuPlano();
                case 4 -> menuAula();
                case 5 -> inscreverAluno();
                case 6 -> cancelarInscricao();
                case 7 -> registrarFrequencia();
                case 8 -> relatorioFrequencia();
                case 0 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida.");
            }

            if (op != 0) {
                pausar();
            }

        } while (op != 0);
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

        if (op == 1) {

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

            System.out.println("Escolha o plano:");

            for (int i = 0; i < planos.size(); i++) {
                System.out.println(i + " - " + planos.get(i).getNome());
            }

            int indicePlano = lerInt();

            if (indicePlano < 0 || indicePlano >= planos.size()) {
                System.out.println("Plano inválido.");
                return;
            }

            Plano plano = planos.get(indicePlano);

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

        else if (op == 2) {

            List<Aluno> alunos = alunoController.consultarTodos();

            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
                return;
            }

            for (Aluno aluno : alunos) {
                System.out.println(aluno.exibirResumo());
                System.out.println("-----------------------------------");
            }
        }
    }

    // ================= INSTRUTOR =================

    private void menuInstrutor() {

        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");

        int op = lerInt();

        if (op == 1) {

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

        else if (op == 2) {

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
    }

    // ================= PLANO =================

    private void menuPlano() {

        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");

        int op = lerInt();

        if (op == 1) {

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

        else if (op == 2) {
            planoController.listar();
        }
    }

    // ================= AULA =================

    private void menuAula() {

        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");

        int op = lerInt();

        if (op == 1) {

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

            System.out.println("Escolha o instrutor:");

            for (int i = 0; i < instrutores.size(); i++) {
                System.out.println(i + " - " + instrutores.get(i).getNome());
            }

            int indiceInstrutor = lerInt();

            if (indiceInstrutor < 0 || indiceInstrutor >= instrutores.size()) {
                System.out.println("Instrutor inválido.");
                return;
            }

            Instrutor instrutor = instrutores.get(indiceInstrutor);

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

        else if (op == 2) {
            aulaController.listar();
        }
    }

    // ================= INSCRIÇÃO =================

    private void inscreverAluno() {

        List<Aluno> alunos = alunoController.consultarTodos();
        List<Aula> aulas = aulaController.consultarTodos();

        if (alunos.isEmpty() || aulas.isEmpty()) {
            System.out.println("Cadastre aluno e aula primeiro.");
            return;
        }

        System.out.println("Escolha o aluno:");

        for (int i = 0; i < alunos.size(); i++) {
            System.out.println(i + " - " + alunos.get(i).getNome());
        }

        int indiceAluno = lerInt();

        if (indiceAluno < 0 || indiceAluno >= alunos.size()) {
            System.out.println("Aluno inválido.");
            return;
        }

        System.out.println("Escolha a aula:");

        for (int i = 0; i < aulas.size(); i++) {
            System.out.println(i + " - " + aulas.get(i).getNome());
        }

        int indiceAula = lerInt();

        if (indiceAula < 0 || indiceAula >= aulas.size()) {
            System.out.println("Aula inválida.");
            return;
        }

        Aluno aluno = alunos.get(indiceAluno);
        Aula aula = aulas.get(indiceAula);

        boolean sucesso = aulaController.inscreverAluno(aluno, aula);

        if (sucesso) {
            System.out.println("Inscrição realizada com sucesso.");
        } else {
            System.out.println("Falha na inscrição.");
        }
    }

    // ================= CANCELAR =================

    private void cancelarInscricao() {

        List<Aluno> alunos = alunoController.consultarTodos();
        List<Aula> aulas = aulaController.consultarTodos();

        if (alunos.isEmpty() || aulas.isEmpty()) {
            System.out.println("Cadastre aluno e aula primeiro.");
            return;
        }

        System.out.println("Escolha o aluno:");

        for (int i = 0; i < alunos.size(); i++) {
            System.out.println(i + " - " + alunos.get(i).getNome());
        }

        int indiceAluno = lerInt();

        if (indiceAluno < 0 || indiceAluno >= alunos.size()) {
            System.out.println("Aluno inválido.");
            return;
        }

        System.out.println("Escolha a aula para cancelar:");

        for (int i = 0; i < aulas.size(); i++) {
            System.out.println(i + " - " + aulas.get(i).getNome());
        }

        int indiceAula = lerInt();

        if (indiceAula < 0 || indiceAula >= aulas.size()) {
            System.out.println("Aula inválida.");
            return;
        }

        Aluno aluno = alunos.get(indiceAluno);
        Aula aula = aulas.get(indiceAula);

        aulaController.cancelarInscricao(aluno, aula);

        System.out.println("Processo de cancelamento finalizado.");
    }

    // ================= FREQUÊNCIA =================

    private void registrarFrequencia() {

        List<Aluno> alunos = alunoController.consultarTodos();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("Escolha o aluno:");

        for (int i = 0; i < alunos.size(); i++) {
            System.out.println(i + " - " + alunos.get(i).getNome());
        }

        int indiceAluno = lerInt();

        if (indiceAluno < 0 || indiceAluno >= alunos.size()) {
            System.out.println("Aluno inválido.");
            return;
        }

        Aluno aluno = alunos.get(indiceAluno);

        Frequencia frequencia = new Frequencia(
                aluno,
                LocalDateTime.now()
        );

        frequenciaController.registrar(frequencia);

        System.out.println("Frequência registrada com sucesso.");
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
}