package service;

import dao.PlanoDAO;
import model.Plano;

import java.util.List;

/**
 * Service responsável pelo gerenciamento de planos.
 *
 * Delega persistência ao PlanoDAO e garante que os planos
 * sobrevivam ao reinício do sistema.
 */
public class PlanoService implements CrudService<Plano> {

    private final PlanoDAO dao;

    /**
     * Construtor padrão.
     */
    public PlanoService() {
        this.dao = new PlanoDAO();
    }

    /**
     * Cria um novo plano e persiste no banco.
     *
     * @param plano plano a ser adicionado
     */
    @Override
    public void criar(Plano plano) {
        if (plano != null) {
            dao.inserir(plano);
        }
    }

    /**
     * Lista todos os planos cadastrados no banco.
     *
     * @return lista de planos
     */
    @Override
    public List<Plano> listar() {
        return dao.listarTodos();
    }

    /**
     * Busca um plano pelo ID no banco.
     *
     * @param id identificador do plano
     * @return plano encontrado ou null
     */
    @Override
    public Plano buscarPorId(int id) {
        if (id <= 0) return null;
        return dao.buscarPorId(id);
    }

    /**
     * Remove um plano pelo ID.
     *
     * @param id identificador do plano
     */
    @Override
    public void remover(int id) {
        if (id > 0) {
            dao.remover(id);
        }
    }

    /**
     * Atualiza os dados de um plano existente no banco.
     *
     * Atualiza: nome, descrição, valor mensal, benefícios, duração.
     *
     * @param id       identificador do plano
     * @param novoPlano objeto com os novos dados
     */
    public void atualizar(int id, Plano novoPlano) {
        if (id <= 0 || novoPlano == null) return;
        Plano existente = dao.buscarPorId(id);
        if (existente != null) {
            dao.atualizar(id, novoPlano);
        }
    }

    /**
     * Método auxiliar para Menu/View.
     *
     * @return lista de planos
     */
    public List<Plano> consultarTodos() {
        return dao.listarTodos();
    }
}
