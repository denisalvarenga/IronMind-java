package service;

import model.Aluno;

public class FrequenciaService {

    private PlanoService planoService = new PlanoService();

    public void registrarEntrada(Aluno aluno) {

        if (aluno.getPlano() == null) {
            System.out.println("Aluno não possui plano.");
            return;
        }

        if (!planoService.planoAtivo(aluno)) {
            System.out.println("Plano vencido. Entrada não permitida.");
            return;
        }

        aluno.registrarEntrada();
        System.out.println("Entrada registrada!");
    }
}