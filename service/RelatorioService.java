package service;

import model.Aluno;
import model.Aula;
import model.Frequencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RelatorioService {

    private PlanoService planoService = new PlanoService();

    public void gerarRelatorioAluno(Aluno aluno) {

        System.out.println("========== RELATORIO DO ALUNO ==========");

        System.out.println("Nome: " + aluno.getNome());
        System.out.println("CPF: " + aluno.getCpf());

        // Plano
        if (aluno.getPlano() != null) {

            boolean ativo = planoService.planoAtivo(aluno);
            LocalDate vencimento = planoService.calcularVencimento(aluno);

            System.out.println("Plano: " + aluno.getPlano().getNome());
            System.out.println("Status: " + (ativo ? "ATIVO" : "VENCIDO"));
            System.out.println("Vencimento: " + vencimento);

        } else {
            System.out.println("Plano: Nenhum");
        }

        // Frequência
        List<Frequencia> frequencias = aluno.getFrequencias();

        int totalVisitas = frequencias.size();
        System.out.println("Total de visitas: " + totalVisitas);

        if (totalVisitas > 0) {
            Frequencia ultima = frequencias.get(totalVisitas - 1);
            LocalDateTime ultimaData = ultima.getDataHora();
            System.out.println("Ultima visita: " + ultimaData);
        } else {
            System.out.println("Ultima visita: Nenhuma");
        }

        // Aulas
        System.out.println("Total de aulas inscritas: " + aluno.getAulas().size());

        System.out.println("========================================");
    }

    // RELATÓRIO POR PERÍODO (REQUISITO)
    public void frequenciaPorPeriodo(Aluno aluno, LocalDate inicio, LocalDate fim) {

        System.out.println("===== FREQUENCIA POR PERIODO =====");

        for (Frequencia f : aluno.getFrequencias()) {

            LocalDate data = f.getDataHora().toLocalDate();

            if ((data.isEqual(inicio) || data.isAfter(inicio)) &&
                    (data.isEqual(fim) || data.isBefore(fim))) {

                System.out.println(f.getDataHora());
            }
        }

        System.out.println("==================================");
    }

    // OCUPAÇÃO DAS AULAS (REQUISITO)
    public void ocupacaoAulas(List<Aula> aulas) {

        System.out.println("===== OCUPAÇÃO DAS AULAS =====");

        for (Aula aula : aulas) {

            int ocupacao = aula.getAlunos().size();
            int capacidade = aula.getCapacidadeMaxima();

            System.out.println(aula.getNome() + ": " + ocupacao + "/" + capacidade);
        }

        System.out.println("================================");
    }
}