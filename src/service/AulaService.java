package service;

import dao.AulaDAO;
import dao.InscricaoDAO;
import model.Aluno;
import model.Aula;

import java.util.List;

/**
 * Service responsável pelo gerenciamento de aulas.
 *
 * Controla CRUD com persistência real via AulaDAO,
 * e orquestra inscrições com validações de negócio via InscricaoDAO.
 */
public class AulaService implements CrudService<Aula> {

    private final AulaDAO dao;
    private final InscricaoDAO inscricaoDAO;

    /**
     * Construtor padrão.
     */
    public AulaService() {
        this.dao = new AulaDAO();
        this.inscricaoDAO = new InscricaoDAO();
    }

    // ===== CRUD =====

    /**
     * Cria uma nova aula e persiste no banco.
     *
     * @param aula aula a ser adicionada
     */
    @Override
    public void criar(Aula aula) {
        if (aula != null) {
            dao.inserir(aula);
        }
    }

    /**
     * Lista todas as aulas cadastradas no banco.
     *
     * @return lista de aulas
     */
    @Override
    public List<Aula> listar() {
        return dao.listarTodos();
    }

    /**
     * Busca uma aula pelo ID no banco.
     *
     * @param id identificador da aula
     * @return aula encontrada ou null
     */
    @Override
    public Aula buscarPorId(int id) {
        if (id <= 0) return null;
        return dao.buscarPorId(id);
    }

    /**
     * Remove uma aula pelo ID.
     *
     * @param id identificador da aula
     */
    @Override
    public void remover(int id) {
        if (id > 0) {
            dao.remover(id);
        }
    }

    /**
     * Atualiza os dados de uma aula no banco.
     *
     * @param id      identificador da aula
     * @param novaAula objeto com os novos dados
     */
    public void atualizar(int id, Aula novaAula) {
        if (id <= 0 || novaAula == null) return;
        Aula existente = dao.buscarPorId(id);
        if (existente != null) {
            dao.atualizar(id, novaAula);
        }
    }

    /**
     * Retorna todas as aulas. Método auxiliar para Menu/View.
     *
     * @return lista de aulas
     */
    public List<Aula> consultarTodos() {
        return dao.listarTodos();
    }

    // ===== REGRA DE NEGÓCIO =====

    /**
     * Realiza a inscrição de um aluno em uma aula via InscricaoDAO.
     *
     * Valida (em ordem):
     * 1. Plano ativo (data_matricula + duracao_meses)
     * 2. Capacidade disponível
     * 3. Conflito de horário
     * 4. Persiste se tudo OK
     *
     * @param aluno aluno a ser inscrito
     * @param aula  aula desejada
     * @return mensagem de resultado
     */
    public String inscrever(Aluno aluno, Aula aula) {
        if (aluno == null || aula == null) {
            return "Aluno ou aula inválidos.";
        }

        String resultado = inscricaoDAO.inscrever(aluno.getId(), aula.getId());
        return resultado.equals("OK") ? "Inscrição realizada com sucesso!" : resultado;
    }

    /**
     * Cancela a inscrição de um aluno em uma aula via InscricaoDAO.
     *
     * @param aluno aluno inscrito
     * @param aula  aula a ser cancelada
     * @return mensagem de resultado
     */
    public String cancelar(Aluno aluno, Aula aula) {
        if (aluno == null || aula == null) {
            return "Aluno ou aula inválidos.";
        }

        int inscricaoId = inscricaoDAO.buscarId(aluno.getId(), aula.getId());

        if (inscricaoId == -1) {
            return "Inscrição não encontrada.";
        }

        inscricaoDAO.remover(inscricaoId);
        return "Inscrição cancelada com sucesso.";
    }

    /**
     * Gera relatório de ocupação das aulas consultando o banco.
     *
     * @return lista textual com nome e ocupação de cada aula
     */
    public List<String> gerarRelatorioOcupacao() {
        List<String> relatorio = new java.util.ArrayList<>();
        for (Aula aula : dao.listarTodos()) {
            relatorio.add(aula.getNome() + " — " +
                    aula.getTotalInscricoes() + "/" + aula.getCapacidade());
        }
        return relatorio;
    }
}
