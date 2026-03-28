package service;

import model.Aluno;

public class FrequenciaService {

    public void registrarEntrada(Aluno aluno) {
        aluno.registrarEntrada();
        System.out.println(" Entrada registrada!");
    }
}