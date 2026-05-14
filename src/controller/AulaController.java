package controller;

import model.Aluno;
import model.Aula;
import service.AulaService;

import java.util.List;

/**
 * Controller responsável pelo gerenciamento de aulas.
 *
 * Faz a comunicação entre View (Menu)
 * e Service.
 */
public class AulaController {

    private final AulaService aulaService;

    /**
     * Construtor padrão.
     */
    public AulaController() {
        this.aulaService = new AulaService();
    }

    /**
     * Cadastra uma nova aula.
     *
     * @param aula aula a ser cadastrada
     */
    public void criar(Aula aula) {
        if (aula != null) {
            aulaService.criar(aula);
            System.out.println("Aula cadastrada com sucesso.");
        } else {
            System.out.println("Aula inválida.");
        }
    }

    /**
     * Lista todas as aulas cadastradas.
     */
    public void listar() {
        List<Aula> aulas = aulaService.listar();

        if (aulas == null || aulas.isEmpty()) {
            System.out.println("Nenhuma aula cadastrada.");
            return;
        }

        System.out.println("===== LISTA DE AULAS =====");

        for (int i = 0; i < aulas.size(); i++) {
            Aula aula = aulas.get(i);

            if (aula != null) {
                System.out.println("ID: " + (i + 1));
                System.out.println(aula.exibirResumo());
                System.out.println("----------------------------");
            }
        }
    }

    /**
     * Retorna todas as aulas.
     *
     * Método auxiliar para Menu.
     *
     * @return lista de aulas
     */
    public List<Aula> consultarTodos() {
        return aulaService.consultarTodos();
    }

    /**
     * Busca uma aula pelo ID.
     *
     * @param id identificador
     * @return aula encontrada ou null
     */
    public Aula buscarPorId(int id) {
        return aulaService.buscarPorId(id);
    }

    /**
     * Atualiza os dados de uma aula.
     *
     * Permite alterar:
     * - horário
     * - capacidade
     * - instrutor
     * - descrição
     * - duração
     *
     * @param id identificador da aula
     * @param novaAula novos dados
     */
    public void atualizar(int id, Aula novaAula) {
        Aula aulaExistente = aulaService.buscarPorId(id);

        if (aulaExistente == null) {
            System.out.println("Aula não encontrada.");
            return;
        }

        if (novaAula == null) {
            System.out.println("Dados inválidos.");
            return;
        }

        aulaService.atualizar(id, novaAula);
        System.out.println("Aula atualizada com sucesso.");
    }

    /**
     * Remove uma aula pelo ID.
     *
     * @param id identificador
     */
    public void remover(int id) {
        Aula aula = aulaService.buscarPorId(id);

        if (aula != null) {
            aulaService.remover(id);
            System.out.println("Aula removida com sucesso.");
        } else {
            System.out.println("Aula não encontrada.");
        }
    }

    /**
     * Realiza a inscrição de um aluno em uma aula.
     *
     * @param aluno aluno
     * @param aula aula
     */
    public void inscreverAluno(Aluno aluno, Aula aula) {
        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return;
        }

        String resposta = aulaService.inscrever(aluno, aula);
        System.out.println(resposta);
    }

    /**
     * Cancela a inscrição de um aluno em uma aula.
     *
     * @param aluno aluno
     * @param aula aula
     */
    public void cancelarInscricao(Aluno aluno, Aula aula) {
        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return;
        }

        String resposta = aulaService.cancelar(aluno, aula);
        System.out.println(resposta);
    }

    /**
     * Exibe relatório de ocupação das aulas.
     */
    public void relatorioOcupacao() {
        List<Aula> aulas = aulaService.listar();

        if (aulas == null || aulas.isEmpty()) {
            System.out.println("Nenhuma aula cadastrada.");
            return;
        }

        System.out.println("===== RELATÓRIO DE OCUPAÇÃO =====");

        for (Aula aula : aulas) {
            if (aula != null) {
                System.out.println(
                        aula.getNome()
                                + " -> "
                                + aula.getTotalInscricoes()
                                + "/"
                                + aula.getCapacidade()
                );
            }
        }
    }
}