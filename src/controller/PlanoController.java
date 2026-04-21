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
            System.out.println("Plano cadastrado com sucesso.");
        } else {
            System.out.println("Plano inválido.");
        }
    }

    // ===== LISTAGEM =====

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

    public List<Plano> consultarTodos() {
        return planoService.consultarTodos();
    }

    // ===== BUSCA =====

    public Plano buscarPorId(int id) {
        return planoService.buscarPorId(id);
    }

    // ===== REMOÇÃO =====

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