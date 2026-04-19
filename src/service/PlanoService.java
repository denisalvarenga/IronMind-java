package service;

import model.Plano;

import java.util.ArrayList;
import java.util.List;

public class PlanoService implements CrudService<Plano> {

    private final List<Plano> planos;

    public PlanoService() {
        this.planos = new ArrayList<>();
        carregarPlanosPadrao();
    }

    // ===== PLANOS PRÉ-CADASTRADOS OBRIGATÓRIOS =====

    private void carregarPlanosPadrao() {

        if (!planos.isEmpty()) {
            return;
        }

        Plano basico = new Plano(
                "Plano Básico",
                "Acesso à musculação em horário comercial",
                89.90,
                "Musculação + avaliação física inicial",
                1
        );

        Plano premium = new Plano(
                "Plano Premium",
                "Acesso completo à academia e aulas coletivas",
                149.90,
                "Musculação + aulas coletivas + avaliação mensal",
                3
        );

        Plano black = new Plano(
                "Plano Black",
                "Plano completo com acesso VIP",
                219.90,
                "Musculação + aulas + personal + acesso VIP",
                6
        );

        planos.add(basico);
        planos.add(premium);
        planos.add(black);
    }

    // ===== CRUD =====

    @Override
    public void criar(Plano plano) {
        if (plano != null) {
            planos.add(plano);
            System.out.println("Plano cadastrado com sucesso.");
        } else {
            System.out.println("Erro: plano inválido.");
        }
    }

    @Override
    public List<Plano> listar() {
        return new ArrayList<>(planos); // ✅ CORRETO
    }

    @Override
    public Plano buscarPorId(int id) {
        if (id <= 0 || id > planos.size()) {
            return null;
        }

        return planos.get(id - 1);
    }

    @Override
    public void remover(int id) {
        Plano plano = buscarPorId(id);

        if (plano != null) {
            planos.remove(plano);
            System.out.println("Plano removido com sucesso.");
        } else {
            System.out.println("Plano não encontrado.");
        }
    }

    // ===== MÉTODO DE EXIBIÇÃO (AGORA CORRETO) =====

    public void exibirPlanos() {
        if (planos.isEmpty()) {
            System.out.println("Nenhum plano cadastrado.");
            return;
        }

        System.out.println("===== PLANOS DISPONÍVEIS =====");

        for (int i = 0; i < planos.size(); i++) {
            Plano plano = planos.get(i);

            System.out.println("ID: " + (i + 1));
            System.out.println("Plano: " + plano.getNome());
            System.out.println("Descrição: " + plano.getDescricao());
            System.out.println("Valor mensal: R$ " + plano.getValorMensal());
            System.out.println("Benefícios: " + plano.getBeneficios());
            System.out.println("Duração: " + plano.getDuracaoMeses() + " meses");
            System.out.println("----------------------------");
        }
    }

    // ===== ENCAPSULAMENTO =====

    public List<Plano> consultarTodos() {
        return new ArrayList<>(planos);
    }
}