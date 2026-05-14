package dao;

import model.Aluno;
import model.Aula;
import model.Plano;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas inscrições de alunos em aulas.
 *
 * Implementa as 3 validações de negócio diretamente em Java (JDBC):
 * 1. Plano ativo
 * 2. Capacidade disponível
 * 3. Conflito de horário
 */
public class InscricaoDAO {

    /**
     * Inscreve um aluno em uma aula após validações de negócio.
     *
     * @param alunoId ID do aluno
     * @param aulaId  ID da aula
     * @return mensagem de resultado ("OK" ou descrição do erro)
     */
    public String inscrever(int alunoId, int aulaId) {

        // 1. Verificar plano ativo
        String statusPlano = verificarPlanoAtivo(alunoId);
        if (!statusPlano.equals("OK")) {
            return statusPlano;
        }

        // 2. Verificar capacidade
        String statusCapacidade = verificarCapacidade(aulaId);
        if (!statusCapacidade.equals("OK")) {
            return statusCapacidade;
        }

        // 3. Verificar conflito de horário
        String statusConflito = verificarConflito(alunoId, aulaId);
        if (!statusConflito.equals("OK")) {
            return statusConflito;
        }

        // 4. Tudo OK — inserir inscrição
        String sql = "INSERT INTO inscricao (aluno_id, aula_id) VALUES (?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alunoId);
            stmt.setInt(2, aulaId);
            stmt.executeUpdate();

            return "OK";

        } catch (SQLException e) {
            return "Erro ao inscrever: " + e.getMessage();
        }
    }

    /**
     * Lista todas as inscrições com JOIN em aluno e aula.
     *
     * @return lista de strings com nome do aluno e da aula
     */
    public List<String> listar() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT a.nome AS aluno_nome, au.nome AS aula_nome, i.data_inscricao " +
                     "FROM inscricao i " +
                     "JOIN aluno a ON i.aluno_id = a.id " +
                     "JOIN aula au ON i.aula_id = au.id " +
                     "ORDER BY i.data_inscricao DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("aluno_nome") + " - " + rs.getString("aula_nome"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar inscrições: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Busca o ID da inscrição pelo aluno e aula.
     *
     * @param alunoId ID do aluno
     * @param aulaId  ID da aula
     * @return ID da inscrição ou -1 se não encontrada
     */
    public int buscarId(int alunoId, int aulaId) {
        String sql = "SELECT id FROM inscricao WHERE aluno_id = ? AND aula_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alunoId);
            stmt.setInt(2, aulaId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar inscrição: " + e.getMessage());
        }

        return -1;
    }

    /**
     * Remove uma inscrição pelo ID.
     *
     * @param inscricaoId ID da inscrição
     */
    public void remover(int inscricaoId) {
        String sql = "DELETE FROM inscricao WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inscricaoId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao remover inscrição: " + e.getMessage());
        }
    }

    // ===================== VALIDAÇÕES EM JAVA (JDBC) =====================

    /**
     * Verifica se o plano do aluno está ativo.
     * Calcula: data_matricula + duracao_meses > hoje
     *
     * @param alunoId ID do aluno
     * @return "OK" se ativo, mensagem de erro caso contrário
     */
    private String verificarPlanoAtivo(int alunoId) {
        String sql = "SELECT a.data_matricula, p.duracao_meses " +
                     "FROM aluno a JOIN plano p ON a.plano_id = p.id WHERE a.id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alunoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return "Aluno não encontrado.";
                }

                LocalDate dataMatricula = rs.getDate("data_matricula").toLocalDate();
                int duracaoMeses = rs.getInt("duracao_meses");
                LocalDate vencimento = dataMatricula.plusMonths(duracaoMeses);

                if (LocalDate.now().isAfter(vencimento)) {
                    return "Plano vencido em: " + vencimento;
                }
            }

        } catch (SQLException e) {
            return "Erro ao verificar plano: " + e.getMessage();
        }

        return "OK";
    }

    /**
     * Verifica se a aula ainda tem vagas disponíveis.
     *
     * @param aulaId ID da aula
     * @return "OK" se há vagas, mensagem de erro caso contrário
     */
    private String verificarCapacidade(int aulaId) {
        String sql = "SELECT capacidade, " +
                     "(SELECT COUNT(*) FROM inscricao WHERE aula_id = ?) AS total_inscritos " +
                     "FROM aula WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, aulaId);
            stmt.setInt(2, aulaId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return "Aula não encontrada.";
                }

                int capacidade = rs.getInt("capacidade");
                int totalInscritos = rs.getInt("total_inscritos");

                if (totalInscritos >= capacidade) {
                    return "Aula lotada (" + totalInscritos + "/" + capacidade + ").";
                }
            }

        } catch (SQLException e) {
            return "Erro ao verificar capacidade: " + e.getMessage();
        }

        return "OK";
    }

    /**
     * Verifica conflito de horário entre a nova aula e as já inscritas pelo aluno.
     *
     * @param alunoId ID do aluno
     * @param aulaId  ID da nova aula
     * @return "OK" se sem conflito, mensagem de erro caso contrário
     */
    private String verificarConflito(int alunoId, int aulaId) {
        // Busca horário e duração da nova aula
        String sqlNova = "SELECT horario, duracao FROM aula WHERE id = ?";

        LocalDateTime novoInicio;
        LocalDateTime novoFim;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlNova)) {

            stmt.setInt(1, aulaId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return "Aula não encontrada.";
                }
                novoInicio = rs.getTimestamp("horario").toLocalDateTime();
                novoFim = novoInicio.plusMinutes(rs.getInt("duracao"));
            }

        } catch (SQLException e) {
            return "Erro ao verificar conflito: " + e.getMessage();
        }

        // Busca aulas já inscritas pelo aluno com JOIN
        String sqlInscritas = "SELECT au.nome, au.horario, au.duracao " +
                              "FROM inscricao i JOIN aula au ON i.aula_id = au.id " +
                              "WHERE i.aluno_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlInscritas)) {

            stmt.setInt(1, alunoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDateTime existenteInicio = rs.getTimestamp("horario").toLocalDateTime();
                    LocalDateTime existenteFim = existenteInicio.plusMinutes(rs.getInt("duracao"));

                    boolean conflito = novoInicio.isBefore(existenteFim) && novoFim.isAfter(existenteInicio);

                    if (conflito) {
                        return "Conflito de horário com a aula: " + rs.getString("nome");
                    }
                }
            }

        } catch (SQLException e) {
            return "Erro ao verificar conflito: " + e.getMessage();
        }

        return "OK";
    }
}
