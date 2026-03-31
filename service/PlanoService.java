package service;

import model.Aluno;

import java.time.LocalDate;

public class PlanoService {

    public boolean planoAtivo(Aluno aluno) {

        if (aluno.getPlano() == null) {
            return false;
        }

        LocalDate vencimento = calcularVencimento(aluno);

        return !LocalDate.now().isAfter(vencimento);
    }

    public LocalDate calcularVencimento(Aluno aluno) {

        return aluno.getDataMatricula()
                .plusMonths(aluno.getPlano().getDuracaoMeses());
    }
}