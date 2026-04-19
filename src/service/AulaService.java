package service;

import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import model.Aula;
import model.InscricaoAula;

public class AulaService implements CrudService<Aula> {

    private final List<Aula> aulas;

    public AulaService() {
        this.aulas = new ArrayList<>();
    }

    // ===== CRUD =====

    @Override
    public void criar(Aula aula) {
        if (aula != null) {
            aulas.add(aula);
            System.out.println("Aula cadastrada com sucesso.");
        } else {
            System.out.println("Erro: aula inválida.");
        }
    }

    @Override
    public List<Aula> listar() {
        return new ArrayList<>(aulas); // ✅ CORRETO
    }

    @Override
    public Aula buscarPorId(int id) {
        if (id <= 0 || id > aulas.size()) {
            return null;
        }
        return aulas.get(id - 1);
    }

    @Override
    public void remover(int id) {
        Aula aula = buscarPorId(id);

        if (aula != null) {
            aulas.remove(aula);
            System.out.println("Aula removida com sucesso.");
        } else {
            System.out.println("Aula não encontrada.");
        }
    }

    public List<Aula> consultarTodos() {
        return new ArrayList<>(aulas);
    }

    // ===== MÉTODO DE EXIBIÇÃO (SEPARADO) =====

    public void exibirAulas() {
        if (aulas.isEmpty()) {
            System.out.println("Nenhuma aula cadastrada.");
            return;
        }

        for (Aula aula : aulas) {
            System.out.println("Aula: " + aula.getNome());
            System.out.println("Descrição: " + aula.getDescricao());
            System.out.println("Instrutor: " + aula.getInstrutor().getNome());
            System.out.println("Horário: " + aula.getHorario());
            System.out.println(
                    "Capacidade: "
                            + aula.getTotalInscricoes()
                            + "/"
                            + aula.getCapacidade()
            );
            System.out.println("----------------------------");
        }
    }

    // ===== REGRA DE NEGÓCIO CRÍTICA =====

    public boolean inscrever(Aluno aluno, Aula aula) {

        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return false;
        }

        // 1. Plano ativo
        if (!aluno.planoAtivo()) {
            System.out.println("Plano vencido em: " + aluno.getVencimentoPlano());
            return false;
        }

        // 2. Capacidade
        if (aula.estaLotada()) {
            System.out.println("Aula lotada.");
            System.out.println(
                    "Capacidade atual: "
                            + aula.getTotalInscricoes()
                            + "/"
                            + aula.getCapacidade()
            );
            return false;
        }

        // 3. Conflito de horário
        for (InscricaoAula inscricao : aluno.listarInscricoes()) {
            if (inscricao.getAula()
                    .getHorario()
                    .equals(aula.getHorario())) {

                System.out.println(
                        "Conflito de horário com a aula: "
                                + inscricao.getAula().getNome()
                );
                return false;
            }
        }

        // 4. Criar inscrição
        InscricaoAula nova = new InscricaoAula(aluno, aula);

        aluno.adicionarInscricao(nova);
        aula.adicionarInscricao(nova);

        System.out.println("Inscrição realizada com sucesso.");
        return true;
    }

    public void cancelar(Aluno aluno, Aula aula) {

        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return;
        }

        InscricaoAula alvo = null;

        for (InscricaoAula inscricao : aluno.listarInscricoes()) {
            if (inscricao.getAula().equals(aula)) {
                alvo = inscricao;
                break;
            }
        }

        if (alvo != null) {
            aluno.removerInscricao(alvo);
            aula.removerInscricao(alvo);
            System.out.println("Inscrição cancelada com sucesso.");
        } else {
            System.out.println("Inscrição não encontrada.");
        }
    }

    public void relatorioOcupacao() {
        if (aulas.isEmpty()) {
            System.out.println("Nenhuma aula cadastrada.");
            return;
        }

        System.out.println("===== RELATÓRIO DE OCUPAÇÃO =====");

        for (Aula aula : aulas) {
            System.out.println(
                    aula.getNome()
                            + " -> "
                            + aula.getTotalInscricoes()
                            + "/"
                            + aula.getCapacidade()
            );
        }
    }
}