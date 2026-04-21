package service;

import java.time.LocalDateTime;
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
        }
    }

    @Override
    public List<Aula> listar() {
        return new ArrayList<>(aulas);
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
        }
    }

    public List<Aula> consultarTodos() {
        return new ArrayList<>(aulas);
    }

    // ===== REGRA DE NEGÓCIO =====

    public String inscrever(Aluno aluno, Aula aula) {

        if (aluno == null || aula == null) {
            return "Aluno ou aula inválidos.";
        }

        // 1. Plano ativo
        if (!aluno.planoAtivo()) {
            return "Plano vencido em: " + aluno.getDataVencimento();
        }

        // 2. Capacidade
        if (aula.estaLotada()) {
            return "Aula lotada. Capacidade: "
                    + aula.getTotalInscricoes()
                    + "/"
                    + aula.getCapacidade();
        }

        // 3. Conflito de horário
        LocalDateTime novoInicio = aula.getHorario();
        LocalDateTime novoFim = novoInicio.plusMinutes(aula.getDuracao());

        for (InscricaoAula inscricao : aluno.listarInscricoes()) {

            Aula aulaExistente = inscricao.getAula();

            LocalDateTime existenteInicio = aulaExistente.getHorario();
            LocalDateTime existenteFim =
                    existenteInicio.plusMinutes(aulaExistente.getDuracao());

            boolean conflito =
                    novoInicio.isBefore(existenteFim)
                            && novoFim.isAfter(existenteInicio);

            if (conflito) {
                return "Conflito com a aula: " + aulaExistente.getNome();
            }
        }

        // 4. OK
        InscricaoAula nova = new InscricaoAula(aluno, aula);

        aluno.adicionarInscricao(nova);
        aula.adicionarInscricao(nova);

        return "Inscrição realizada com sucesso!";
    }

    public String cancelar(Aluno aluno, Aula aula) {

        if (aluno == null || aula == null) {
            return "Aluno ou aula inválidos.";
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
            return "Inscrição cancelada com sucesso.";
        }

        return "Inscrição não encontrada.";
    }

    // ===== RELATÓRIO DE OCUPAÇÃO =====

    public List<String> gerarRelatorioOcupacao() {

        List<String> relatorio = new ArrayList<>();

        for (Aula aula : aulas) {
            String linha = aula.getNome() + " - "
                    + aula.getTotalInscricoes()
                    + "/"
                    + aula.getCapacidade();

            relatorio.add(linha);
        }

        return relatorio;
    }
}