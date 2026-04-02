package service;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AulaService implements CrudService<Aula> {

    private List<Aula> aulas = new ArrayList<>();

    public void criar(Aula a) {
        aulas.add(a);
    }

    public List<Aula> listar() {
        return aulas;
    }

    public void atualizar(int index, Aula a) {
        aulas.set(index, a);
    }

    public void remover(int index) {
        aulas.remove(index);
    }

    public boolean inscrever(Aluno aluno, Aula aula) {

        LocalDate vencimento = aluno.getDataMatricula()
                .plusMonths(aluno.getPlano().getDuracaoMeses());

        if (LocalDate.now().isAfter(vencimento)) {
            System.out.println("Plano vencido em: " + vencimento);
            return false;
        }

        if (aula.estaLotada()) {
            System.out.println("Aula lotada.");
            return false;
        }

        for (InscricaoAula i : aluno.getInscricoes()) {
            if (i.getAula().getHorario().equals(aula.getHorario())) {
                System.out.println("Conflito de horário.");
                return false;
            }
        }

        InscricaoAula nova = new InscricaoAula(aluno, aula);

        aluno.adicionarInscricao(nova);
        aula.getInscricoes().add(nova);

        return true;
    }

    public void cancelar(Aluno aluno, Aula aula) {

        InscricaoAula alvo = null;

        for (InscricaoAula i : aluno.getInscricoes()) {
            if (i.getAula().equals(aula)) {
                alvo = i;
                break;
            }
        }

        if (alvo != null) {
            aluno.removerInscricao(alvo);
            aula.getInscricoes().remove(alvo);
        }
    }

    public void relatorioOcupacao() {
        for (Aula a : aulas) {
            System.out.println(
                    a.getNome() + " -> " +
                            a.getInscricoes().size() + "/" + a.getCapacidade()
            );
        }
    }
}