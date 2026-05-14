package dao;

import model.Plano;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela persistência de planos no PostgreSQL.
 *
 * Fornece CRUD completo com PreparedStatement e garante
 * que o ID do banco seja corretamente atribuído aos objetos retornados.
 */
public class PlanoDAO {

    /**
     * Insere um novo plano no banco.
     *
     * @param plano plano a ser persistido
     */
    public void inserir(Plano plano) {

        String sql = """
            INSERT INTO plano
            (nome, descricao, valor_mensal, beneficios, duracao_meses)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getDescricao());
            stmt.setDouble(3, plano.getValorMensal());
            stmt.setString(4, plano.getBeneficios());
            stmt.setInt(5, plano.getDuracaoMeses());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir plano: " + e.getMessage());
        }
    }

    /**
     * Busca um plano pelo ID, retornando com o ID do banco corretamente setado.
     *
     * @param id identificador do plano
     * @return plano encontrado ou null
     */
    public Plano buscarPorId(int id) {

        String sql = "SELECT * FROM plano WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarPlano(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar plano: " + e.getMessage());
        }

        return null;
    }

    /**
     * Lista todos os planos cadastrados com seus IDs do banco.
     *
     * @return lista de planos
     */
    public List<Plano> listarTodos() {

        List<Plano> lista = new ArrayList<>();

        String sql = "SELECT * FROM plano ORDER BY nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montarPlano(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar planos: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Atualiza os dados de um plano existente.
     *
     * @param id    identificador do plano
     * @param plano objeto com os novos dados
     */
    public void atualizar(int id, Plano plano) {

        String sql = """
            UPDATE plano
            SET nome = ?, descricao = ?, valor_mensal = ?, beneficios = ?, duracao_meses = ?
            WHERE id = ?
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getDescricao());
            stmt.setDouble(3, plano.getValorMensal());
            stmt.setString(4, plano.getBeneficios());
            stmt.setInt(5, plano.getDuracaoMeses());
            stmt.setInt(6, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar plano: " + e.getMessage());
        }
    }

    /**
     * Remove um plano pelo ID.
     *
     * @param id identificador do plano
     */
    public void remover(int id) {

        String sql = "DELETE FROM plano WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao remover plano: " + e.getMessage());
        }
    }

    /**
     * Monta objeto Plano a partir do ResultSet, incluindo o ID do banco.
     *
     * @param rs resultado da consulta SQL
     * @return objeto Plano com todos os campos preenchidos
     * @throws SQLException erro de leitura do ResultSet
     */
    private Plano montarPlano(ResultSet rs) throws SQLException {
        return new Plano(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getDouble("valor_mensal"),
                rs.getString("beneficios"),
                rs.getInt("duracao_meses")
        );
    }
}
