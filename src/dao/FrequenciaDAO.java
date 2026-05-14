package dao;

import model.Frequencia;
import model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela persistência de frequências.
 *
 * Registra entradas de alunos e permite consultar histórico.
 */
public class FrequenciaDAO {

    /**
     * Registra uma frequência pelo ID da inscrição.
     *
     * @param inscricaoId ID da inscrição do aluno na aula
     */
    public void registrar(int inscricaoId) {

        String sql = "INSERT INTO frequencia (inscricao_id) VALUES (?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inscricaoId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao registrar frequência: " + e.getMessage());
        }
    }

    /**
     * Registra frequência diretamente pelo ID do aluno.
     * Busca a primeira inscrição ativa do aluno e registra entrada.
     *
     * @param alunoId ID do aluno
     */
    public void registrarPorAluno(int alunoId) {
        String sqlBuscaInscricao = "SELECT id FROM inscricao WHERE aluno_id = ? LIMIT 1";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBuscaInscricao)) {

            stmt.setInt(1, alunoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    registrar(rs.getInt("id"));
                } else {
                    System.out.println("Nenhuma inscrição encontrada para o aluno.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao registrar frequência por aluno: " + e.getMessage());
        }
    }

    /**
     * Lista todas as frequências com JOIN em inscricao e aluno.
     *
     * @return lista de frequências
     */
    public List<Frequencia> listarTodos() {

        List<Frequencia> lista = new ArrayList<>();

        String sql = "SELECT f.data_hora, a.nome, a.cpf, a.telefone, a.email, " +
                     "a.data_nascimento, a.data_matricula " +
                     "FROM frequencia f " +
                     "JOIN inscricao i ON f.inscricao_id = i.id " +
                     "JOIN aluno a ON i.aluno_id = a.id " +
                     "ORDER BY f.data_hora DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Aluno aluno = new Aluno(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getString("email"),
                        rs.getDate("data_matricula").toLocalDate(),
                        null
                );

                Frequencia f = new Frequencia(
                        aluno,
                        rs.getTimestamp("data_hora").toLocalDateTime()
                );

                lista.add(f);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar frequências: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Remove uma frequência pelo ID.
     *
     * @param id identificador da frequência
     */
    public void remover(int id) {

        String sql = "DELETE FROM frequencia WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao remover frequência: " + e.getMessage());
        }
    }

    /**
     * Conta o total de visitas de um aluno.
     *
     * @param alunoId ID do aluno
     * @return total de visitas
     */
    public int contarVisitas(int alunoId) {
        String sql = "SELECT COUNT(*) AS total FROM frequencia f " +
                     "JOIN inscricao i ON f.inscricao_id = i.id WHERE i.aluno_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alunoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao contar visitas: " + e.getMessage());
        }

        return 0;
    }
}
