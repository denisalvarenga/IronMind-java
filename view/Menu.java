package view;

import model.Aluno;
import model.Aula;
import model.Instrutor;
import model.Plano;
import service.*;

import java.util.*;

public class Menu {

    private Scanner sc = new Scanner(System.in);

    private List<Aluno> alunos = new ArrayList<>();
    private List<Aula> aulas = new ArrayList<>();
    private List<Instrutor> instrutores = new ArrayList<>();
    private List<Plano> planos = new ArrayList<>();

    private Map<String, String> logins = new HashMap<>();

    private AlunoService alunoService = new AlunoService(alunos);
    private InstrutorService instrutorService = new InstrutorService(instrutores);
    private AulaService aulaService = new AulaService();
    private FrequenciaService frequenciaService = new FrequenciaService();
    private RelatorioService relatorioService = new RelatorioService();

    public Menu() {
        logins.put("12345678900", "123456");

        planos.add(new Plano("Standard", 99.90, 1));
        planos.add(new Plano("Confort", 130.00, 1));
        planos.add(new Plano("Silver", 230.00, 1));
    }

    private void limparTela() {
        for (int i = 0; i < 30; i++) System.out.println();
    }

    private void header() {
        System.out.println("        o-.-.-<===>-.-.-o");
        System.out.println("            IRON MIND");
        System.out.println("        o-.-.-<===>-.-.-o\n");
    }

    private void center(String text) {
        System.out.println("        " + text);
    }

    public void exibir() {

        while (true) {

            limparTela();
            header();

            center("1 - Administrativo IronMind");
            center("2 - Aluno");
            center("3 - Sair");

            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    menuAdminGeral();
                    break;
                case 2:
                    menuAlunoLogin();
                    break;
                case 3:
                    return;
            }
        }
    }

    // ================= ADMIN =================

    private void menuAdminGeral() {

        while (true) {

            limparTela();
            header();

            center("1 - Administrativo IronMind");
            center("2 - Instrutor IronMind");
            center("3 - Voltar");

            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {

                case 1:
                    if (login()) menuAdmin();
                    break;

                case 2:
                    if (login()) menuInstrutor();
                    break;

                case 3:
                    return;
            }
        }
    }

    private boolean login() {

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        if (logins.containsKey(cpf) && logins.get(cpf).equals(senha)) return true;

        System.out.println("Acesso negado.");
        return false;
    }

    private void menuAdmin() {

        while (true) {

            limparTela();
            header();

            center("1 - Cadastrar Administrativo");
            center("2 - Atualizar aluno");
            center("3 - Remover aluno");
            center("4 - Listar alunos");
            center("5 - Relatório aluno");

            center("6 - Cadastrar instrutor");
            center("7 - Atualizar instrutor");
            center("8 - Remover instrutor");

            center("0 - Voltar");

            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {

                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    atualizarAluno();
                    break;
                case 3:
                    removerAluno();
                    break;
                case 4:
                    alunoService.listarAlunos();
                    break;
                case 5:
                    relatorioAluno();
                    break;
                case 6:
                    cadastrarInstrutor();
                    break;
                case 7:
                    atualizarInstrutor();
                    break;
                case 8:
                    removerInstrutor();
                    break;
                case 0:
                    return;
            }
        }
    }

    // ================= INSTRUTOR =================

    private void menuInstrutor() {

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        Instrutor i = instrutorService.buscarPorNome(nome);

        if (i == null) {
            System.out.println("Instrutor não encontrado.");
            return;
        }

        while (true) {

            limparTela();
            header();

            center("1 - Bater ponto");
            center("2 - Atualizar dados");
            center("0 - Voltar");

            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    System.out.println("Ponto registrado.");
                    break;
                case 2:
                    atualizarInstrutor();
                    break;
                case 0:
                    return;
            }
        }
    }

    // ================= ALUNO =================

    private void menuAlunoLogin() {

        while (true) {

            limparTela();
            header();

            center("1 - Acessar conta");
            center("2 - Cadastrar acesso");
            center("3 - Voltar");

            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {

                case 1:
                    acessarAluno();
                    break;

                case 2:
                    cadastrarAcessoAluno();
                    break;

                case 3:
                    return;
            }
        }
    }

    private void acessarAluno() {

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        if (!logins.containsKey(cpf) || !logins.get(cpf).equals(senha)) {
            System.out.println("Acesso negado.");
            return;
        }

        Aluno aluno = alunoService.buscarPorCpf(cpf);

        if (aluno != null) menuAluno(aluno);
        else System.out.println("Aluno não encontrado.");
    }

    private void cadastrarAcessoAluno() {

        System.out.println("Escolha seu plano:");

        for (int i = 0; i < planos.size(); i++) {
            System.out.println((i + 1) + " - " + planos.get(i).getNome());
        }

        int op = sc.nextInt();
        sc.nextLine();

        Plano planoEscolhido = planos.get(op - 1);

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Telefone: ");
        String tel = sc.nextLine();

        Aluno aluno = new Aluno(nome, cpf);
        aluno.setEmail(email);
        aluno.setTelefone(tel);
        aluno.setPlano(planoEscolhido);

        alunoService.cadastrarAluno(aluno);

        System.out.print("Crie uma senha: ");
        String senha = sc.nextLine();

        logins.put(cpf, senha);

        System.out.println("Cadastro realizado com sucesso!");
    }

    private void menuAluno(Aluno aluno) {

        while (true) {

            limparTela();
            header();

            center("1 - Check-in");
            center("2 - Atualizar dados");
            center("3 - Trocar plano");
            center("4 - Inscrever aula");
            center("5 - Cancelar inscrição");
            center("6 - Cancelar plano");
            center("0 - Voltar");

            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {

                case 1:
                    frequenciaService.registrarEntrada(aluno);
                    break;
                case 2:
                    atualizarAluno();
                    break;
                case 3:
                    escolherPlano(aluno);
                    break;
                case 4:
                    inscreverAluno(aluno);
                    break;
                case 5:
                    cancelarInscricao(aluno);
                    break;
                case 6:
                    aluno.setPlano(null);
                    break;
                case 0:
                    return;
            }
        }
    }

    // ================= AUX =================

    private void cadastrarFuncionario() {

        System.out.print("CPF: ");
        String login = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        logins.put(login, senha);
    }

    private void atualizarAluno() {

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Telefone: ");
        String tel = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        alunoService.atualizarAluno(cpf, tel, email);
    }

    private void relatorioAluno() {

        System.out.print("CPF: ");
        Aluno aluno = alunoService.buscarPorCpf(sc.nextLine());

        if (aluno != null) {
            relatorioService.gerarRelatorioAluno(aluno);
        }
    }

    private void cadastrarInstrutor() {

        Instrutor i = new Instrutor();

        System.out.print("Nome: ");
        i.setNome(sc.nextLine());

        System.out.print("Especialidade: ");
        i.setEspecialidade(sc.nextLine());

        instrutorService.cadastrarInstrutor(i);

        System.out.print("Senha: ");
        logins.put(i.getNome(), sc.nextLine());
    }

    private void atualizarInstrutor() {

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Especialidade: ");
        String esp = sc.nextLine();

        instrutorService.atualizarInstrutor(nome, esp);
    }

    private void removerInstrutor() {

        System.out.print("Nome: ");
        instrutorService.removerInstrutor(sc.nextLine());
    }

    private void removerAluno() {

        System.out.print("CPF: ");
        alunoService.removerAluno(sc.nextLine());
    }

    private void escolherPlano(Aluno aluno) {

        for (int i = 0; i < planos.size(); i++) {
            System.out.println((i + 1) + " - " + planos.get(i).getNome());
        }

        int op = sc.nextInt();
        sc.nextLine();

        aluno.setPlano(planos.get(op - 1));
    }

    private void inscreverAluno(Aluno aluno) {

        System.out.print("Nome da aula: ");
        String nome = sc.nextLine();

        for (Aula a : aulas) {
            if (a.getNome().equalsIgnoreCase(nome)) {
                aulaService.inscreverAluno(aluno, a);
                return;
            }
        }

        System.out.println("Aula não encontrada.");
    }

    private void cancelarInscricao(Aluno aluno) {

        System.out.print("Nome da aula: ");
        String nome = sc.nextLine();

        for (Aula a : aulas) {
            if (a.getNome().equalsIgnoreCase(nome)) {
                aulaService.cancelarInscricao(aluno, a);
                return;
            }
        }

        System.out.println("Aula não encontrada.");
    }
}