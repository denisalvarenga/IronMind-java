package service;

import dao.FrequenciaDAO;
import model.Aluno;
import model.Frequencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsável pelo gerenciamento de frequências.
 *
 * Registra entradas de alunos no banco via FrequenciaDAO
 * e gera relatórios de período.
 */
public class FrequenciaService {

    private final FrequenciaDAO dao = new FrequenciaDAO();

    /**
     * Registra entrada de um aluno na academia.
     *
     * Persiste no banco via FrequenciaDAO usando o ID real do aluno.
     *
     * @param aluno aluno que está entrando
     */
    public void registrarEntrada(Aluno aluno) {
        try {
            if (aluno == null || aluno.getId() <= 0) {
                System.out.println("Aluno inválido ou sem ID do banco.");
                return;
            }
            dao.registrarPorAluno(aluno.getId());
        } catch (Exception e) {
            System.out.println("Erro ao registrar entrada: " + e.getMessage());
        }
    }

    /**
     * Lista frequências persistidas no banco de dados.
     *
     * @return lista de frequências do banco
     */
    public List<Frequencia> listarDoBanco() {
        try {
            return dao.listarTodos();
        } catch (Exception e) {
            System.out.println("Erro ao listar frequências do banco: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista todas as frequências (delega ao banco).
     *
     * @return lista de frequências
     */
    public List<Frequencia> listar() {
        return listarDoBanco();
    }

    /**
     * Exibe frequências dentro de um período específico.
     *
     * @param frequencias lista de frequências a filtrar
     * @param inicio      data de início do período
     * @param fim         data de fim do período
     */
    public void relatorioPeriodo(List<Frequencia> frequencias, LocalDate inicio, LocalDate fim) {
        try {
            for (Frequencia f : frequencias) {
                if (!f.getDataHora().toLocalDate().isBefore(inicio) &&
                        !f.getDataHora().toLocalDate().isAfter(fim)) {
                    System.out.println(f.exibirResumo());
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório de período: " + e.getMessage());
        }
    }
}
