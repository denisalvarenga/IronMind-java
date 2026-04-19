package controller;

import model.Instrutor;
import service.InstrutorService;

import java.util.List;

public class InstrutorController {

    private InstrutorService instrutorService;

    public InstrutorController() {
        this.instrutorService = new InstrutorService();
    }

    public void cadastrarInstrutor(Instrutor instrutor) {
        instrutorService.criar(instrutor);
    }

    public List<Instrutor> listarInstrutores() {
        return instrutorService.listar();
    }
}