package service;

import model.Aluno;
import model.Frequencia;
import model.Plano;

import java.time.LocalDate;
import java.util.List;

public class AlunoService {

    private List<Aluno> alunos;

    public AlunoService(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    // INSERIR
    public void cadastrarAluno(Aluno aluno) {

        if (buscarPorCpf(aluno.getCpf()) != null) {
            System.out.println("Já existe um aluno com esse CPF.");
            return;
        }

        alunos.add(aluno);
        System.out.println("Aluno cadastrado com sucesso.");
    }

    // LISTAR
    public void listarAlunos() {

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        for (Aluno a : alunos) {
            System.out.println("Nome: " + a.getNome());
            System.out.println("CPF: " + a.getCpf());
            System.out.println("----------------------");
        }
    }

    // BUSCAR
    public Aluno buscarPorCpf(String cpf) {

        for (Aluno a : alunos) {
            if (a.getCpf().equals(cpf)) {
                return a;
            }
        }
        return null;
    }

    // ATUALIZAR
    public void atualizarAluno(String cpf, String telefone, String email) {

        Aluno aluno = buscarPorCpf(cpf);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        aluno.setTelefone(telefone);
        aluno.setEmail(email);

        System.out.println("Aluno atualizado com sucesso.");
    }

    // REMOVER
    public void removerAluno(String cpf) {

        Aluno aluno = buscarPorCpf(cpf);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        alunos.remove(aluno);
        System.out.println("Aluno removido com sucesso.");
    }

    // DETALHE DO ALUNO (AGORA COMPLETO)
    public void exibirDetalhesAluno(String cpf) {

        Aluno aluno = buscarPorCpf(cpf);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("===== DETALHES DO ALUNO =====");
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("CPF: " + aluno.getCpf());

        // PLANO
        Plano plano = aluno.getPlano();

        if (plano != null) {
            System.out.println("Plano: " + plano.getNome());

            LocalDate vencimento = aluno.getDataMatricula().plusMonths(plano.getDuracaoMeses());
            System.out.println("Data de vencimento: " + vencimento);

            if (LocalDate.now().isAfter(vencimento)) {
                System.out.println("Status do plano: VENCIDO");
            } else {
                System.out.println("Status do plano: ATIVO");
            }

        } else {
            System.out.println("Plano: Nenhum");
        }

        // AULAS
        System.out.println("Aulas inscritas: " + aluno.getAulas().size());

        // FREQUÊNCIA
        List<Frequencia> frequencias = aluno.getFrequencias();

        System.out.println("Total de visitas: " + frequencias.size());

        if (!frequencias.isEmpty()) {
            Frequencia ultima = frequencias.get(frequencias.size() - 1);
            System.out.println("Última visita: " + ultima.getDataHora());
        } else {
            System.out.println("Última visita: Nenhuma");
        }

        System.out.println("==============================");
    }
}