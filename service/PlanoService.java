package service;

import model.Aluno;
import java.time.LocalDate;

public class PlanoService {

    public boolean planoAtivo(Aluno aluno) {

        if (aluno.getPlano() == null) {
            return false;
        }

        LocalDate dataMatricula = aluno.getDataMatricula();
        int duracao = aluno.getPlano().getDuracaoMeses();

        LocalDate vencimento = dataMatricula.plusMonths(duracao);

        return LocalDate.now().isBefore(vencimento);
    }

    public LocalDate calcularVencimento(Aluno aluno) {
        return aluno.getDataMatricula()
                .plusMonths(aluno.getPlano().getDuracaoMeses());
    }
}