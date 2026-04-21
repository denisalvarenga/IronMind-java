package controller;

import model.Instrutor;
import service.InstrutorService;

import java.util.List;

public class InstrutorController {

    private final InstrutorService instrutorService;

    public InstrutorController() {
        this.instrutorService = new InstrutorService();
    }

    // ===== CADASTRO =====

    public void cadastrarInstrutor(Instrutor instrutor) {
        if (instrutor != null) {
            instrutorService.criar(instrutor);
        } else {
            System.out.println("Instrutor inválido.");
        }
    }

    // ===== LISTAGEM =====

    public List<Instrutor> listarInstrutores() {
        return instrutorService.listar();
    }
}