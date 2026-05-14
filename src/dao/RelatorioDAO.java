package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável por consultas de relatório com JOIN.
 *
 * Garante pelo menos 3 consultas com JOIN para o requisito de banco.
 */
public class RelatorioDAO {

    /**
     * Lista alunos com seus planos (JOIN aluno → plano).
     *
     * @return lista de strings com nome do aluno e plano
     */
    public List<String> alunosComPlano() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT a.nome, p.nome AS plano " +
                     "FROM aluno a " +
                     "JOIN plano p ON a.plano_id = p.id " +
                     "ORDER BY a.nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("nome") + " - Plano: " + rs.getString("plano"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao gerar relatório alunos/plano: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Lista inscrições com nome do aluno e nome da aula
     * (JOIN inscricao → aluno → aula).
     *
     * @return lista de strings com aluno e aula
     */
    public List<String> inscricoesComDetalhes() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT a.nome AS aluno_nome, au.nome AS aula_nome, au.horario " +
                     "FROM inscricao i " +
                     "JOIN aluno a ON i.aluno_id = a.id " +
                     "JOIN aula au ON i.aula_id = au.id " +
                     "ORDER BY au.horario";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("aluno_nome") + " → " +
                          rs.getString("aula_nome") + " (" + rs.getTimestamp("horario") + ")");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao gerar relatório inscrições: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Lista frequências com nome do aluno e horário da visita
     * (JOIN frequencia → inscricao → aluno).
     *
     * @return lista de strings com aluno e data/hora da visita
     */
    public List<String> frequenciasComAluno() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT a.nome AS aluno_nome, f.data_hora " +
                     "FROM frequencia f " +
                     "JOIN inscricao i ON f.inscricao_id = i.id " +
                     "JOIN aluno a ON i.aluno_id = a.id " +
                     "ORDER BY f.data_hora DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("aluno_nome") + " - " + rs.getTimestamp("data_hora"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao gerar relatório frequências: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Busca resumo completo de um aluno: visitas, aulas inscritas e status do plano.
     * (JOIN aluno → plano → inscricao → frequencia)
     *
     * @param alunoId ID do aluno
     * @return string com resumo ou mensagem de erro
     */
    public String resumoAluno(int alunoId) {
        String sql = "SELECT a.nome, p.nome AS plano, " +
                     "(a.data_matricula + (p.duracao_meses || ' months')::interval) AS vencimento, " +
                     "CASE WHEN CURRENT_DATE > (a.data_matricula + (p.duracao_meses || ' months')::interval) " +
                     "     THEN 'VENCIDO' ELSE 'ATIVO' END AS status_plano, " +
                     "COUNT(DISTINCT i.id) AS total_aulas, " +
                     "COUNT(f.id) AS total_visitas, " +
                     "MAX(f.data_hora) AS ultima_visita " +
                     "FROM aluno a " +
                     "JOIN plano p ON a.plano_id = p.id " +
                     "LEFT JOIN inscricao i ON a.id = i.aluno_id " +
                     "LEFT JOIN frequencia f ON i.id = f.inscricao_id " +
                     "WHERE a.id = ? " +
                     "GROUP BY a.nome, p.nome, p.duracao_meses, a.data_matricula";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alunoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return "Aluno: " + rs.getString("nome") +
                           "\nPlano: " + rs.getString("plano") +
                           "\nStatus: " + rs.getString("status_plano") +
                           "\nVencimento: " + rs.getDate("vencimento") +
                           "\nTotal de Visitas: " + rs.getInt("total_visitas") +
                           "\nÚltima Visita: " + rs.getTimestamp("ultima_visita") +
                           "\nAulas Inscritas: " + rs.getInt("total_aulas");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar resumo do aluno: " + e.getMessage());
        }

        return "Aluno não encontrado.";
    }
}
