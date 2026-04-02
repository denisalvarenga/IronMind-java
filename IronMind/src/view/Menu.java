package view;

import model.*;
import service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Menu {

    private Scanner sc = new Scanner(System.in);

    private AlunoService alunoService = new AlunoService();
    private InstrutorService instrutorService = new InstrutorService();
    private PlanoService planoService = new PlanoService();
    private AulaService aulaService = new AulaService();
    private FrequenciaService frequenciaService = new FrequenciaService();

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
            }

            pausar();

        } while (op != 0);
    }

    // ================= LIMPA TELA REAL =================
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

    // ================= PAUSA =================
    private void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        sc.nextLine();
        sc.nextLine();
    }

    // ================= UTIL =================
    private int lerInt() {
        while (!sc.hasNextInt()) {
            System.out.println("Digite um número válido.");
            sc.next();
        }
        return sc.nextInt();
    }

    // ================= ALUNO =================
    private void menuAluno() {

        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");

        int op = lerInt();

        if (op == 1) {

            if (planoService.listar().isEmpty()) {
                System.out.println("Cadastre um plano primeiro.");
                return;
            }

            sc.nextLine();

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("CPF: ");
            String cpf = sc.nextLine();

            System.out.print("Telefone: ");
            String tel = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Data nascimento (AAAA-MM-DD): ");
            LocalDate nasc = LocalDate.parse(sc.nextLine());

            System.out.println("Escolha o plano:");
            for (int i = 0; i < planoService.listar().size(); i++) {
                System.out.println(i + " - " + planoService.listar().get(i).getNome());
            }

            int idx = lerInt();
            Plano plano = planoService.listar().get(idx);

            Aluno a = new Aluno(
                    nome, cpf, tel,
                    nasc,
                    email,
                    LocalDate.now(),
                    plano
            );

            alunoService.criar(a);
            System.out.println("Aluno cadastrado.");

        } else if (op == 2) {

            if (alunoService.listar().isEmpty()) {
                System.out.println("Nenhum aluno.");
                return;
            }

            for (Aluno a : alunoService.listar()) {

                boolean ativo = alunoService.planoAtivo(a);

                System.out.println(
                        a.getNome() +
                                " | Plano: " + (ativo ? "ATIVO" : "VENCIDO")
                );
            }
        }
    }

    // ================= INSTRUTOR =================
    private void menuInstrutor() {

        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");

        int op = lerInt();

        if (op == 1) {

            sc.nextLine();

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("CPF: ");
            String cpf = sc.nextLine();

            System.out.print("Telefone: ");
            String tel = sc.nextLine();

            System.out.print("Especialidade: ");
            String esp = sc.nextLine();

            System.out.print("Horário de trabalho: ");
            String horario = sc.nextLine();

            instrutorService.criar(
                    new Instrutor(nome, cpf, tel, esp, horario)
            );

            System.out.println("Instrutor cadastrado.");

        } else if (op == 2) {

            if (instrutorService.listar().isEmpty()) {
                System.out.println("Nenhum instrutor.");
                return;
            }

            for (Instrutor i : instrutorService.listar()) {
                System.out.println(
                        i.getNome() +
                                " - " + i.getEspecialidade() +
                                " | Horário: " + i.getHorarioTrabalho()
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

            sc.nextLine();

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Descrição: ");
            String desc = sc.nextLine();

            System.out.print("Valor mensal: ");
            double valor = sc.nextDouble();

            sc.nextLine();

            System.out.print("Benefícios: ");
            String ben = sc.nextLine();

            System.out.print("Duração (meses): ");
            int meses = lerInt();

            Plano p = new Plano(nome, desc, valor, ben, meses);

            planoService.criar(p);
            System.out.println("Plano cadastrado.");

        } else if (op == 2) {

            if (planoService.listar().isEmpty()) {
                System.out.println("Nenhum plano.");
                return;
            }

            for (Plano p : planoService.listar()) {
                System.out.println(
                        p.getNome() +
                                " | " + p.getDescricao() +
                                " | R$" + p.getValorMensal() +
                                " | " + p.getBeneficios() +
                                " | " + p.getDuracaoMeses() + " meses"
                );
            }
        }
    }

    // ================= AULA =================
    private void menuAula() {

        System.out.println("1 - Criar");
        System.out.println("2 - Listar");

        int op = lerInt();

        if (op == 1) {

            if (instrutorService.listar().isEmpty()) {
                System.out.println("Cadastre um instrutor primeiro.");
                return;
            }

            sc.nextLine();

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Descrição: ");
            String desc = sc.nextLine();

            System.out.print("Duração (min): ");
            int duracao = lerInt();

            System.out.print("Capacidade: ");
            int cap = lerInt();

            System.out.println("Escolha instrutor:");
            for (int i = 0; i < instrutorService.listar().size(); i++) {
                System.out.println(i + " - " + instrutorService.listar().get(i).getNome());
            }

            int idx = lerInt();
            Instrutor inst = instrutorService.listar().get(idx);

            Aula aula = new Aula(
                    nome,
                    desc,
                    duracao,
                    LocalDateTime.now().plusHours(1),
                    cap,
                    inst
            );

            aulaService.criar(aula);
            System.out.println("Aula criada.");

        } else if (op == 2) {

            if (aulaService.listar().isEmpty()) {
                System.out.println("Nenhuma aula.");
                return;
            }

            for (Aula a : aulaService.listar()) {
                System.out.println(
                        a.getNome() +
                                " | " + a.getDescricao() +
                                " | " + a.getDuracao() + " min" +
                                " | Instrutor: " + a.getInstrutor().getNome()
                );
            }
        }
    }

    // ================= INSCRIÇÃO =================
    private void inscreverAluno() {

        if (alunoService.listar().isEmpty() || aulaService.listar().isEmpty()) {
            System.out.println("Cadastre aluno e aula primeiro.");
            return;
        }

        System.out.println("Escolha aluno:");
        for (int i = 0; i < alunoService.listar().size(); i++) {
            System.out.println(i + " - " + alunoService.listar().get(i).getNome());
        }
        int aIdx = lerInt();

        System.out.println("Escolha aula:");
        for (int i = 0; i < aulaService.listar().size(); i++) {
            System.out.println(i + " - " + aulaService.listar().get(i).getNome());
        }
        int auIdx = lerInt();

        boolean ok = aulaService.inscrever(
                alunoService.listar().get(aIdx),
                aulaService.listar().get(auIdx)
        );

        System.out.println(ok ? "Inscrito com sucesso." : "Falha na inscrição.");
    }

    // ================= CANCELAR =================
    private void cancelarInscricao() {

        System.out.println("Escolha aluno:");
        for (int i = 0; i < alunoService.listar().size(); i++) {
            System.out.println(i + " - " + alunoService.listar().get(i).getNome());
        }
        int aIdx = lerInt();

        System.out.println("Escolha aula:");
        for (int i = 0; i < aulaService.listar().size(); i++) {
            System.out.println(i + " - " + aulaService.listar().get(i).getNome());
        }
        int auIdx = lerInt();

        aulaService.cancelar(
                alunoService.listar().get(aIdx),
                aulaService.listar().get(auIdx)
        );

        System.out.println("Inscrição cancelada.");
    }

    // ================= FREQUÊNCIA =================
    private void registrarFrequencia() {

        if (alunoService.listar().isEmpty()) {
            System.out.println("Nenhum aluno.");
            return;
        }

        System.out.println("Escolha aluno:");
        for (int i = 0; i < alunoService.listar().size(); i++) {
            System.out.println(i + " - " + alunoService.listar().get(i).getNome());
        }

        int idx = lerInt();

        frequenciaService.registrarEntrada(
                alunoService.listar().get(idx)
        );

        System.out.println("Entrada registrada.");
    }

    private void relatorioFrequencia() {

        if (alunoService.listar().isEmpty()) {
            System.out.println("Nenhum aluno.");
            return;
        }

        System.out.println("Escolha aluno:");
        for (int i = 0; i < alunoService.listar().size(); i++) {
            System.out.println(i + " - " + alunoService.listar().get(i).getNome());
        }

        int idx = lerInt();

        frequenciaService.relatorioPeriodo(
                alunoService.listar().get(idx),
                LocalDate.now().minusDays(30),
                LocalDate.now()
        );
    }
}