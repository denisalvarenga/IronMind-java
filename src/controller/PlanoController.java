package controller;

import model.Plano;
import service.PlanoService;

import java.util.List;

/**
 * Controller responsável pelo gerenciamento de planos.
 *
 * Faz a comunicação entre View (Menu)
 * e Service.
 */
public class PlanoController {

    private final PlanoService planoService;

    /**
     * Construtor padrão.
     */
    public PlanoController() {
        this.planoService = new PlanoService();
    }

    /**
     * Cria um novo plano.
     *
     * @param plano plano a ser cadastrado
     */
    public void criar(Plano plano) {
        if (plano != null) {
            planoService.criar(plano);
            System.out.println("Plano cadastrado com sucesso.");
        } else {
            System.out.println("Plano inválido.");
        }
    }

    /**
     * Lista todos os planos cadastrados.
     */
    public void listar() {
        List<Plano> planos = planoService.listar();

        if (planos.isEmpty()) {
            System.out.println("Nenhum plano cadastrado.");
            return;
        }

        System.out.println("===== PLANOS DISPONÍVEIS =====");

        for (int i = 0; i < planos.size(); i++) {
            Plano plano = planos.get(i);

            System.out.println("ID: " + (i + 1));
            System.out.println(plano.exibirResumo());
            System.out.println("----------------------------");
        }
    }

    /**
     * Retorna todos os planos.
     *
     * Método auxiliar para Menu.
     *
     * @return lista de planos
     */
    public List<Plano> consultarTodos() {
        return planoService.consultarTodos();
    }

    /**
     * Busca um plano pelo ID.
     *
     * @param id identificador
     * @return plano encontrado ou null
     */
    public Plano buscarPorId(int id) {
        return planoService.buscarPorId(id);
    }

    /**
     * Atualiza os dados de um plano.
     *
     * Permite alterar:
     * - nome
     * - descrição
     * - valor mensal
     * - benefícios
     * - duração
     *
     * @param id identificador do plano
     * @param novoPlano novos dados
     */
    public void atualizar(int id, Plano novoPlano) {
        Plano planoExistente = planoService.buscarPorId(id);

        if (planoExistente == null) {
            System.out.println("Plano não encontrado.");
            return;
        }

        if (novoPlano == null) {
            System.out.println("Dados inválidos.");
            return;
        }

        planoService.atualizar(id, novoPlano);
        System.out.println("Plano atualizado com sucesso.");
    }

    /**
     * Remove um plano pelo ID.
     *
     * @param id identificador
     */
    public void remover(int id) {
        Plano plano = planoService.buscarPorId(id);

        if (plano != null) {
            planoService.remover(id);
            System.out.println("Plano removido com sucesso.");
        } else {
            System.out.println("Plano não encontrado.");
        }
    }
}