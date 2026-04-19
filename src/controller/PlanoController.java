package controller;

import model.Plano;
import service.PlanoService;

import java.util.List;

public class PlanoController {

    private final PlanoService planoService;

    public PlanoController() {
        this.planoService = new PlanoService();
    }

    // ===== CADASTRO =====

    public void criar(Plano plano) {
        if (plano != null) {
            planoService.criar(plano);
        } else {
            System.out.println("Plano inválido.");
        }
    }

    // ===== LISTAGEM =====

    public void listar() {
        planoService.listar();
    }

    public List<Plano> consultarTodos() {
        return planoService.consultarTodos();
    }

    // ===== BUSCA =====

    public Plano buscarPorId(int id) {
        return planoService.buscarPorId(id);
    }

    // ===== REMOÇÃO =====

    public void remover(int id) {
        planoService.remover(id);
    }
}