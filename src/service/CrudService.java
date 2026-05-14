package service;

import java.util.List;

/**
 * Interface genérica de CRUD para os services do sistema.
 *
 * Define operações básicas de criação, listagem, busca e remoção.
 * Implementada por AlunoService, AulaService, InstrutorService e PlanoService.
 * Demonstra uso de polimorfismo via interface.
 *
 * @param <T> tipo da entidade gerenciada
 */
public interface CrudService<T> {

    /**
     * Cria/persiste uma nova entidade.
     *
     * @param obj objeto a ser criado
     */
    void criar(T obj);

    /**
     * Lista todas as entidades cadastradas.
     *
     * @return lista de entidades
     */
    List<T> listar();

    /**
     * Busca uma entidade pelo ID.
     *
     * @param id identificador
     * @return entidade encontrada ou null
     */
    T buscarPorId(int id);

    /**
     * Remove uma entidade pelo ID.
     *
     * @param id identificador
     */
    void remover(int id);
}
