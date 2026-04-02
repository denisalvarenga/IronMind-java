package service;

import java.util.List;

// Interface genérica de CRUD.

public interface CrudService<T> {

    void criar(T obj);
    List<T> listar();
    void atualizar(int index, T obj);
    void remover(int index);
}