package dao;

import model.Aluno;
import model.Plano;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela persistência de alunos.
 */
public class AlunoDAO {

    /**
     * Insere um novo aluno no banco.
     *
     * @param aluno aluno a ser persistido
     */
    public void inserir(Aluno aluno) {
        String sql = """
                INSERT INTO aluno
                (nome, cpf, telefone, data_nascimento, email, data_matricula, plano_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getTelefone());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(5, aluno.getEmail());
            stmt.setDate(6, Date.valueOf(aluno.getDataMatricula()));

            if (aluno.getPlano() != null) {
                stmt.setInt(7, aluno.getPlano().getId());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lista todos os alunos cadastrados.
     *
     * @return lista de alunos
     */
    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();

        String sql = """
                SELECT a.*, 
                       p.id AS plano_id,
                       p.nome AS plano_nome,
                       p.descricao AS plano_descricao,
                       p.valor_mensal,
                       p.beneficios,
                       p.duracao_meses
                FROM aluno a
                LEFT JOIN plano p ON a.plano_id = p.id
                """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarAluno(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    /**
     * Busca um aluno pelo ID.
     *
     * @param id identificador do aluno
     * @return aluno encontrado ou null
     */
    public Aluno buscarPorId(int id) {
        String sql = """
                SELECT a.*, 
                       p.id AS plano_id,
                       p.nome AS plano_nome,
                       p.descricao AS plano_descricao,
                       p.valor_mensal,
                       p.beneficios,
                       p.duracao_meses
                FROM aluno a
                LEFT JOIN plano p ON a.plano_id = p.id
                WHERE a.id = ?
                """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarAluno(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Remove um aluno pelo ID.
     *
     * @param id identificador do aluno
     */
    public void remover(int id) {
        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Atualiza os dados de um aluno no banco.
     *
     * @param id    identificador do aluno
     * @param aluno objeto com novos dados
     */
    public void atualizar(int id, Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, cpf = ?, telefone = ?, " +
                     "data_nascimento = ?, email = ?, data_matricula = ?, plano_id = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getTelefone());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(5, aluno.getEmail());
            stmt.setDate(6, Date.valueOf(aluno.getDataMatricula()));

            if (aluno.getPlano() != null) {
                stmt.setInt(7, aluno.getPlano().getId());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            stmt.setInt(8, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Monta o objeto Aluno a partir do ResultSet.
     *
     * @param rs resultado da consulta
     * @return objeto aluno
     * @throws SQLException erro de leitura
     */
    private Aluno montarAluno(ResultSet rs) throws SQLException {
        Plano plano = null;

        if (rs.getObject("plano_id") != null) {
            plano = new Plano(
                    rs.getInt("plano_id"),
                    rs.getString("plano_nome"),
                    rs.getString("plano_descricao"),
                    rs.getDouble("valor_mensal"),
                    rs.getString("beneficios"),
                    rs.getInt("duracao_meses")
            );
        }

        Aluno aluno = new Aluno(
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("telefone"),
                rs.getDate("data_nascimento").toLocalDate(),
                rs.getString("email"),
                rs.getDate("data_matricula").toLocalDate(),
                plano
        );

        aluno.setId(rs.getInt("id"));
        return aluno;
    }
}