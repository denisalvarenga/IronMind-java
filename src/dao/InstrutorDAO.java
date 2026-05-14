package dao;

import model.Instrutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela persistência de instrutores no PostgreSQL.
 *
 * Fornece CRUD completo com PreparedStatement e garante
 * que o ID do banco seja corretamente atribuído aos objetos retornados.
 */
public class InstrutorDAO {

    /**
     * Insere um novo instrutor no banco.
     *
     * @param instrutor instrutor a ser persistido
     */
    public void inserir(Instrutor instrutor) {

        String sql = """
            INSERT INTO instrutor
            (nome, cpf, telefone, especialidade, horario_trabalho)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, instrutor.getNome());
            stmt.setString(2, instrutor.getCpf());
            stmt.setString(3, instrutor.getTelefone());
            stmt.setString(4, instrutor.getEspecialidade());
            stmt.setString(5, instrutor.getHorarioTrabalho());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir instrutor: " + e.getMessage());
        }
    }

    /**
     * Busca um instrutor pelo ID, com o ID do banco corretamente setado.
     *
     * @param id identificador do instrutor
     * @return instrutor encontrado ou null
     */
    public Instrutor buscarPorId(int id) {

        String sql = "SELECT * FROM instrutor WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarInstrutor(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar instrutor: " + e.getMessage());
        }

        return null;
    }

    /**
     * Lista todos os instrutores cadastrados com seus IDs do banco.
     *
     * @return lista de instrutores
     */
    public List<Instrutor> listarTodos() {

        List<Instrutor> lista = new ArrayList<>();

        String sql = "SELECT * FROM instrutor ORDER BY nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarInstrutor(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar instrutores: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Atualiza os dados de um instrutor existente.
     *
     * @param id        identificador do instrutor
     * @param instrutor objeto com os novos dados
     */
    public void atualizar(int id, Instrutor instrutor) {

        String sql = """
            UPDATE instrutor
            SET nome = ?, cpf = ?, telefone = ?, especialidade = ?, horario_trabalho = ?
            WHERE id = ?
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, instrutor.getNome());
            stmt.setString(2, instrutor.getCpf());
            stmt.setString(3, instrutor.getTelefone());
            stmt.setString(4, instrutor.getEspecialidade());
            stmt.setString(5, instrutor.getHorarioTrabalho());
            stmt.setInt(6, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar instrutor: " + e.getMessage());
        }
    }

    /**
     * Remove um instrutor pelo ID.
     *
     * @param id identificador do instrutor
     */
    public void remover(int id) {

        String sql = "DELETE FROM instrutor WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao remover instrutor: " + e.getMessage());
        }
    }

    /**
     * Monta objeto Instrutor a partir do ResultSet, incluindo o ID do banco.
     *
     * @param rs resultado da consulta SQL
     * @return objeto Instrutor com todos os campos preenchidos
     * @throws SQLException erro de leitura do ResultSet
     */
    private Instrutor montarInstrutor(ResultSet rs) throws SQLException {
        Instrutor instrutor = new Instrutor(
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("telefone"),
                rs.getString("especialidade"),
                rs.getString("horario_trabalho")
        );
        instrutor.setId(rs.getInt("id"));
        return instrutor;
    }
}
