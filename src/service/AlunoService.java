package service;

import model.Aluno;
import model.Frequencia;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlunoService implements CrudService<Aluno> {

    private final List<Aluno> alunos;

    public AlunoService() {
        this.alunos = new ArrayList<>();
    }

    // ===== CRUD =====

    @Override
    public void criar(Aluno aluno) {
        if (aluno != null) {
            alunos.add(aluno);
            System.out.println("Aluno cadastrado com sucesso.");
        } else {
            System.out.println("Erro: aluno inválido.");
        }
    }

    @Override
    public List<Aluno> listar() {
        return new ArrayList<>(alunos); // ✅ CORRETO
    }

    @Override
    public Aluno buscarPorId(int id) {
        if (id <= 0 || id > alunos.size()) {
            return null;
        }

        return alunos.get(id - 1);
    }

    @Override
    public void remover(int id) {
        Aluno aluno = buscarPorId(id);

        if (aluno != null) {
            alunos.remove(aluno);
            System.out.println("Aluno removido com sucesso.");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    // ===== EXIBIÇÃO (SEPARADA) =====

    public void exibirAlunos() {
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("===== LISTA DE ALUNOS =====");

        for (int i = 0; i < alunos.size(); i++) {
            Aluno aluno = alunos.get(i);

            System.out.println("ID: " + (i + 1));
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("CPF: " + aluno.getCpf());
            System.out.println("Telefone: " + aluno.getTelefone());
            System.out.println("Plano: " + aluno.getPlano().getNome());

            String statusPlano = aluno.planoAtivo()
                    ? "ATIVO"
                    : "VENCIDO";

            System.out.println(
                    "Status do Plano: "
                            + statusPlano
                            + " | Vencimento: "
                            + aluno.getVencimentoPlano()
            );

            System.out.println(
                    "Número de aulas inscritas: "
                            + aluno.getTotalInscricoes()
            );

            System.out.println(
                    "Total de visitas: "
                            + aluno.getTotalFrequencias()
            );

            LocalDateTime ultimaVisita = buscarUltimaVisita(aluno);

            System.out.println(
                    "Última visita: "
                            + (ultimaVisita != null
                            ? ultimaVisita
                            : "Nenhuma registrada")
            );

            System.out.println("----------------------------");
        }
    }

    // ===== CONSULTA SEGURA =====

    public List<Aluno> consultarTodos() {
        return new ArrayList<>(alunos);
    }

    // ===== REGRA DE NEGÓCIO =====

    private LocalDateTime buscarUltimaVisita(Aluno aluno) {

        LocalDateTime ultima = null;

        for (Frequencia frequencia : aluno.listarFrequencias()) {

            if (frequencia.getDataHora() != null) {

                if (ultima == null ||
                        frequencia.getDataHora().isAfter(ultima)) {

                    ultima = frequencia.getDataHora();
                }
            }
        }

        return ultima;
    }
}