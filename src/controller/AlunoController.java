package controller;

import model.Aluno;
import service.AlunoService;

import java.util.List;

/**
 * Controller responsável pelo gerenciamento de alunos.
 *
 * Faz a comunicação entre View (Menu)
 * e Service.
 */
public class AlunoController {

    private final AlunoService alunoService;

    /**
     * Construtor padrão.
     */
    public AlunoController() {
        this.alunoService = new AlunoService();
    }

    /**
     * Cadastra um novo aluno.
     *
     * @param aluno aluno a ser cadastrado
     */
    public void cadastrarAluno(Aluno aluno) {
        if (aluno != null) {
            alunoService.criar(aluno);
            System.out.println("Aluno cadastrado com sucesso.");
        } else {
            System.out.println("Aluno inválido.");
        }
    }

    /**
     * Lista todos os alunos cadastrados.
     */
    public void listarAlunos() {
        List<Aluno> alunos = alunoService.listar();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("===== LISTA DE ALUNOS =====");

        for (int i = 0; i < alunos.size(); i++) {
            Aluno aluno = alunos.get(i);

            System.out.println("ID: " + (i + 1));
            System.out.println(aluno.exibirResumo());
            System.out.println("----------------------------");
        }
    }

    /**
     * Retorna todos os alunos.
     *
     * Método auxiliar para Menu.
     *
     * @return lista de alunos
     */
    public List<Aluno> consultarTodos() {
        return alunoService.consultarTodos();
    }

    /**
     * Busca um aluno pelo ID.
     *
     * @param id identificador
     * @return aluno encontrado ou null
     */
    public Aluno buscarAluno(int id) {
        return alunoService.buscarPorId(id);
    }

    /**
     * Atualiza completamente os dados de um aluno.
     *
     * Permite alterar:
     * - nome
     * - CPF
     * - telefone
     * - email
     * - data de nascimento
     * - data de matrícula
     * - plano
     *
     * @param id identificador do aluno
     * @param novoAluno novos dados
     */
    public void atualizarAluno(int id, Aluno novoAluno) {
        Aluno alunoExistente = alunoService.buscarPorId(id);

        if (alunoExistente == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        if (novoAluno == null) {
            System.out.println("Dados inválidos.");
            return;
        }

        alunoService.atualizar(id, novoAluno);
        System.out.println("Aluno atualizado com sucesso.");
    }

    /**
     * Remove um aluno pelo ID.
     *
     * @param id identificador
     */
    public void removerAluno(int id) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno != null) {
            alunoService.remover(id);
            System.out.println("Aluno removido com sucesso.");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    /**
     * Verifica se o plano do aluno está ativo.
     *
     * @param aluno aluno
     * @return true se estiver ativo
     */
    public boolean planoAtivo(Aluno aluno) {
        if (aluno == null) {
            return false;
        }

        return aluno.planoAtivo();
    }

    /**
     * Exibe o resumo de uma lista de pessoas usando polimorfismo.
     *
     * O método chama exibirResumo() de Pessoa — implementado
     * diferentemente por Aluno e Instrutor (polimorfismo).
     *
     * @param pessoas lista de objetos que herdam de Pessoa
     */
    public void exibirResumos(List<model.Pessoa> pessoas) {
        if (pessoas == null || pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa para exibir.");
            return;
        }
        System.out.println("===== RESUMO (POLIMORFISMO) =====");
        for (model.Pessoa p : pessoas) {
            // Chama exibirResumo() — implementação depende do tipo real (Aluno ou Instrutor)
            System.out.println(p.exibirResumo());
            System.out.println("---");
        }
    }
}