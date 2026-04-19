package controller;

import model.Frequencia;
import service.FrequenciaService;

import java.util.List;

public class FrequenciaController {

    private FrequenciaService frequenciaService;

    public FrequenciaController() {
        this.frequenciaService = new FrequenciaService();
    }

    public void registrar(Frequencia frequencia) {
        frequenciaService.registrarEntrada(frequencia.getAluno());
    }

    public List<Frequencia> listar() {
        return frequenciaService.listar();
    }
}