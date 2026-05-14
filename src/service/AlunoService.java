package service;

import dao.AlunoDAO;
import model.Aluno;
import model.Plano;

import java.util.List;

/**
 * Service responsável pelo gerenciamento de alunos.
 *
 * Controla regras de negócio e faz a comunicação
 * entre Controller e DAO.
 */
public class AlunoService implements CrudService<Aluno> {

    private final AlunoDAO dao;

    /**
     * Construtor padrão.
     */
    public AlunoService() {
        this.dao = new AlunoDAO();
    }

    /**
     * Cria um novo aluno.
     *
     * @param aluno aluno a ser persistido
     */
    @Override
    public void criar(Aluno aluno) {
        if (aluno != null) {
            dao.inserir(aluno);
        }
    }

    /**
     * Lista todos os alunos cadastrados.
     *
     * @return lista de alunos
     */
    @Override
    public List<Aluno> listar() {
        return dao.listarTodos();
    }

    /**
     * Busca um aluno pelo ID.
     *
     * @param id identificador do aluno
     * @return aluno encontrado ou null
     */
    @Override
    public Aluno buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }

        return dao.buscarPorId(id);
    }

    /**
     * Remove um aluno pelo ID.
     *
     * @param id identificador do aluno
     */
    @Override
    public void remover(int id) {
        if (id > 0) {
            dao.remover(id);
        }
    }

    /**
     * Atualiza completamente os dados de um aluno.
     *
     * Atualiza:
     * - nome
     * - CPF
     * - telefone
     * - email
     * - data de nascimento
     * - data de matrícula
     * - plano
     *
     * Preserva ID e mantém consistência
     * entre objetos e persistência.
     *
     * @param id identificador do aluno
     * @param novoAluno objeto com novos dados
     */
    public void atualizar(int id, Aluno novoAluno) {
        Aluno alunoExistente = buscarPorId(id);

        if (alunoExistente == null || novoAluno == null) {
            return;
        }

        Plano planoAntigo = alunoExistente.getPlano();
        Plano novoPlano = novoAluno.getPlano();

        alunoExistente.setNome(novoAluno.getNome());
        alunoExistente.setCpf(novoAluno.getCpf());
        alunoExistente.setTelefone(novoAluno.getTelefone());
        alunoExistente.setEmail(novoAluno.getEmail());
        alunoExistente.setDataNascimento(novoAluno.getDataNascimento());
        alunoExistente.setDataMatricula(novoAluno.getDataMatricula());

        if (planoAntigo != null && planoAntigo != novoPlano) {
            planoAntigo.removerAluno(alunoExistente);
        }

        alunoExistente.setPlano(novoPlano);

        if (novoPlano != null && novoPlano != planoAntigo) {
            novoPlano.adicionarAluno(alunoExistente);
        }

        /*
         * Persistência via UPDATE real no banco.
         */
        dao.atualizar(id, alunoExistente);
    }

    /**
     * Método auxiliar para Menu/View.
     *
     * @return lista de alunos
     */
    public List<Aluno> consultarTodos() {
        return dao.listarTodos();
    }
}