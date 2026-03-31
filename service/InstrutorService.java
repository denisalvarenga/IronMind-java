package service;

import model.Instrutor;

import java.util.List;

public class InstrutorService {

    private List<Instrutor> instrutores;

    public InstrutorService(List<Instrutor> instrutores) {
        this.instrutores = instrutores;
    }

    // INSERIR
    public void cadastrarInstrutor(Instrutor instrutor) {

        if (buscarPorNome(instrutor.getNome()) != null) {
            System.out.println("Já existe um instrutor com esse nome.");
            return;
        }

        instrutores.add(instrutor);
        System.out.println("Instrutor cadastrado com sucesso.");
    }

    // LISTAR
    public void listarInstrutores() {

        if (instrutores.isEmpty()) {
            System.out.println("Nenhum instrutor cadastrado.");
            return;
        }

        for (Instrutor i : instrutores) {
            System.out.println("Nome: " + i.getNome());
            System.out.println("Especialidade: " + i.getEspecialidade());
            System.out.println("----------------------");
        }
    }

    // BUSCAR
    public Instrutor buscarPorNome(String nome) {

        for (Instrutor i : instrutores) {
            if (i.getNome().equalsIgnoreCase(nome)) {
                return i;
            }
        }
        return null;
    }

    // ATUALIZAR
    public void atualizarInstrutor(String nome, String novaEspecialidade) {

        Instrutor instrutor = buscarPorNome(nome);

        if (instrutor == null) {
            System.out.println("Instrutor não encontrado.");
            return;
        }

        instrutor.setEspecialidade(novaEspecialidade);
        System.out.println("Instrutor atualizado com sucesso.");
    }

    // REMOVER
    public void removerInstrutor(String nome) {

        Instrutor instrutor = buscarPorNome(nome);

        if (instrutor == null) {
            System.out.println("Instrutor não encontrado.");
            return;
        }

        instrutores.remove(instrutor);
        System.out.println("Instrutor removido com sucesso.");
    }

    // DETALHE DO INSTRUTOR (SEM getAulas, SEM ERRO)
    public void exibirDetalhesInstrutor(String nome) {

        Instrutor instrutor = buscarPorNome(nome);

        if (instrutor == null) {
            System.out.println("Instrutor não encontrado.");
            return;
        }

        System.out.println("===== DADOS DO INSTRUTOR =====");
        System.out.println("Nome: " + instrutor.getNome());
        System.out.println("Especialidade: " + instrutor.getEspecialidade());
        System.out.println("==============================");
    }
}