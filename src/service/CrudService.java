package service;

import java.util.List;

public interface CrudService<T> {

    void criar(T obj);

    List<T> listar(); // corrigido

    T buscarPorId(int id);

    void remover(int id);
}