package controller;

import model.Frequencia;
import service.FrequenciaService;

import java.util.List;

public class FrequenciaController {

    private final FrequenciaService frequenciaService;

    public FrequenciaController() {
        this.frequenciaService = new FrequenciaService();
    }

    // ===== REGISTRO =====

    public boolean registrar(Frequencia frequencia) {

        if (frequencia != null && frequencia.getAluno() != null) {
            frequenciaService.registrarEntrada(frequencia.getAluno());
            return true;
        } else {
            System.out.println("Frequência inválida.");
            return false;
        }
    }

    // ===== LISTAGEM =====

    public List<Frequencia> listar() {
        return frequenciaService.listar();
    }
}