package dao;

import model.Aula;
import model.Instrutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela persistência de aulas.
 *
 * Fornece CRUD completo com JOIN ao instrutor.
 */
public class AulaDAO {

    /**
     * Insere uma nova aula no banco.
     *
     * @param aula aula a ser persistida
     */
    public void inserir(Aula aula) {
        String sql = "INSERT INTO aula (nome, descricao, duracao, horario, capacidade, instrutor_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aula.getNome());
            stmt.setString(2, aula.getDescricao());
            stmt.setInt(3, aula.getDuracao());
            stmt.setTimestamp(4, Timestamp.valueOf(aula.getHorario()));
            stmt.setInt(5, aula.getCapacidade());

            if (aula.getInstrutor() != null) {
                stmt.setInt(6, aula.getInstrutor().getId());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir aula: " + e.getMessage());
        }
    }

    /**
     * Lista todas as aulas com JOIN ao instrutor.
     *
     * @return lista de aulas
     */
    public List<Aula> listarTodos() {

        List<Aula> lista = new ArrayList<>();

        String sql = "SELECT a.id AS aula_id, a.nome AS aula_nome, a.descricao, a.duracao, " +
                     "a.horario, a.capacidade, " +
                     "i.id AS instrutor_id, i.nome AS instrutor_nome, " +
                     "i.cpf, i.telefone, i.especialidade, i.horario_trabalho " +
                     "FROM aula a JOIN instrutor i ON a.instrutor_id = i.id";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarAula(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar aulas: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Busca uma aula pelo ID com JOIN ao instrutor.
     *
     * @param id identificador da aula
     * @return aula encontrada ou null
     */
    public Aula buscarPorId(int id) {
        String sql = "SELECT a.id AS aula_id, a.nome AS aula_nome, a.descricao, a.duracao, " +
                     "a.horario, a.capacidade, " +
                     "i.id AS instrutor_id, i.nome AS instrutor_nome, " +
                     "i.cpf, i.telefone, i.especialidade, i.horario_trabalho " +
                     "FROM aula a JOIN instrutor i ON a.instrutor_id = i.id WHERE a.id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarAula(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar aula: " + e.getMessage());
        }

        return null;
    }

    /**
     * Atualiza os dados de uma aula existente.
     *
     * @param id   identificador da aula
     * @param aula objeto com os novos dados
     */
    public void atualizar(int id, Aula aula) {
        String sql = "UPDATE aula SET nome = ?, descricao = ?, duracao = ?, horario = ?, capacidade = ?, instrutor_id = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aula.getNome());
            stmt.setString(2, aula.getDescricao());
            stmt.setInt(3, aula.getDuracao());
            stmt.setTimestamp(4, Timestamp.valueOf(aula.getHorario()));
            stmt.setInt(5, aula.getCapacidade());

            if (aula.getInstrutor() != null) {
                stmt.setInt(6, aula.getInstrutor().getId());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            stmt.setInt(7, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aula: " + e.getMessage());
        }
    }

    /**
     * Remove uma aula pelo ID.
     *
     * @param id identificador da aula
     */
    public void remover(int id) {
        String sql = "DELETE FROM aula WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao remover aula: " + e.getMessage());
        }
    }

    /**
     * Monta objeto Aula a partir do ResultSet.
     *
     * @param rs resultado da consulta
     * @return objeto Aula
     * @throws SQLException erro de leitura
     */
    private Aula montarAula(ResultSet rs) throws SQLException {
        Instrutor instrutor = new Instrutor(
                rs.getString("instrutor_nome"),
                rs.getString("cpf"),
                rs.getString("telefone"),
                rs.getString("especialidade"),
                rs.getString("horario_trabalho")
        );
        instrutor.setId(rs.getInt("instrutor_id"));

        Aula aula = new Aula(
                rs.getString("aula_nome"),
                rs.getString("descricao"),
                rs.getInt("duracao"),
                rs.getTimestamp("horario").toLocalDateTime(),
                rs.getInt("capacidade"),
                instrutor
        );
        aula.setId(rs.getInt("aula_id"));

        return aula;
    }
}
