package service;

import dao.InstrutorDAO;
import model.Instrutor;

import java.util.List;

/**
 * Service responsável pelo gerenciamento de instrutores.
 *
 * Controla as regras de negócio e delega persistência ao InstrutorDAO,
 * garantindo que os dados sobrevivam ao reinício do sistema.
 */
public class InstrutorService implements CrudService<Instrutor> {

    private final InstrutorDAO dao;

    /**
     * Construtor padrão.
     */
    public InstrutorService() {
        this.dao = new InstrutorDAO();
    }

    /**
     * Cria um novo instrutor e persiste no banco.
     *
     * @param instrutor instrutor a ser adicionado
     */
    @Override
    public void criar(Instrutor instrutor) {
        if (instrutor != null) {
            dao.inserir(instrutor);
        }
    }

    /**
     * Lista todos os instrutores cadastrados no banco.
     *
     * @return lista de instrutores
     */
    @Override
    public List<Instrutor> listar() {
        return dao.listarTodos();
    }

    /**
     * Busca um instrutor pelo ID no banco.
     *
     * @param id identificador do instrutor
     * @return instrutor encontrado ou null
     */
    @Override
    public Instrutor buscarPorId(int id) {
        if (id <= 0) return null;
        return dao.buscarPorId(id);
    }

    /**
     * Remove um instrutor pelo ID.
     *
     * @param id identificador do instrutor
     */
    @Override
    public void remover(int id) {
        if (id > 0) {
            dao.remover(id);
        }
    }

    /**
     * Atualiza os dados de um instrutor no banco.
     *
     * Atualiza: nome, CPF, telefone, especialidade, horário de trabalho.
     *
     * @param id            identificador do instrutor
     * @param novoInstrutor objeto com os novos dados
     */
    public void atualizar(int id, Instrutor novoInstrutor) {
        if (id <= 0 || novoInstrutor == null) return;
        Instrutor existente = dao.buscarPorId(id);
        if (existente != null) {
            dao.atualizar(id, novoInstrutor);
        }
    }

    /**
     * Método auxiliar para Menu/View.
     *
     * @return lista de instrutores
     */
    public List<Instrutor> consultarTodos() {
        return dao.listarTodos();
    }
}
