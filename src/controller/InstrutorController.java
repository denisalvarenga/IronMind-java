package controller;

import model.Instrutor;
import service.InstrutorService;

import java.util.List;

/**
 * Controller responsável pelo gerenciamento de instrutores.
 *
 * Faz a comunicação entre View (Menu)
 * e Service.
 */
public class InstrutorController {

    private final InstrutorService instrutorService;

    /**
     * Construtor padrão.
     */
    public InstrutorController() {
        this.instrutorService = new InstrutorService();
    }

    /**
     * Cadastra um novo instrutor.
     *
     * @param instrutor instrutor a ser cadastrado
     */
    public void cadastrarInstrutor(Instrutor instrutor) {
        if (instrutor != null) {
            instrutorService.criar(instrutor);
            System.out.println("Instrutor cadastrado com sucesso.");
        } else {
            System.out.println("Instrutor inválido.");
        }
    }

    /**
     * Lista todos os instrutores cadastrados.
     *
     * @return lista de instrutores
     */
    public List<Instrutor> listarInstrutores() {
        return instrutorService.listar();
    }

    /**
     * Busca um instrutor pelo ID.
     *
     * @param id identificador
     * @return instrutor encontrado ou null
     */
    public Instrutor buscarPorId(int id) {
        return instrutorService.buscarPorId(id);
    }

    /**
     * Atualiza os dados de um instrutor.
     *
     * Permite alterar:
     * - nome
     * - CPF
     * - telefone
     * - especialidade
     * - horário de trabalho
     *
     * @param id identificador do instrutor
     * @param novoInstrutor novos dados
     */
    public void atualizarInstrutor(int id, Instrutor novoInstrutor) {
        Instrutor instrutorExistente = instrutorService.buscarPorId(id);

        if (instrutorExistente == null) {
            System.out.println("Instrutor não encontrado.");
            return;
        }

        if (novoInstrutor == null) {
            System.out.println("Dados inválidos.");
            return;
        }

        instrutorService.atualizar(id, novoInstrutor);
        System.out.println("Instrutor atualizado com sucesso.");
    }

    /**
     * Remove um instrutor pelo ID.
     *
     * @param id identificador
     */
    public void removerInstrutor(int id) {
        Instrutor instrutor = instrutorService.buscarPorId(id);

        if (instrutor != null) {
            instrutorService.remover(id);
            System.out.println("Instrutor removido com sucesso.");
        } else {
            System.out.println("Instrutor não encontrado.");
        }
    }
}