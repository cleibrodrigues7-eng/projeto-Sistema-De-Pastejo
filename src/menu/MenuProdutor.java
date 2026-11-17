package menu;

import cadastro.Produtor;
import gerenciador.GerenciadorArquivos;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuProdutor {

    private static String formatarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) return "";
        String[] palavras = nome.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : palavras) {
            if (!p.isEmpty()) {
                sb.append(Character.toUpperCase(p.charAt(0)))
                        .append(p.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    private static boolean isNomeValido(String nome) {
        return nome != null && nome.matches("[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+");
    }

    private static boolean isTelefoneValido(String telefone) {
        return telefone != null && telefone.matches("\\d{10,11}");
    }

    private static boolean isCpfValido(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }

    private static int gerarProximoIdRecursivo(List<Produtor> lista, int indice, int maiorIdAtual) {
        if (indice >= lista.size()) {
            return maiorIdAtual + 1;
        }
        int idAtual = lista.get(indice).getIdProdutor();
        int novoMaior = Math.max(maiorIdAtual, idAtual);
        return gerarProximoIdRecursivo(lista, indice + 1, novoMaior);
    }

    public static void gerenciar(Scanner scanner) {
        int opcao = -1;

        do {
            System.out.println("\n===================================");
            System.out.println("       GERENCIAR PRODUTORES        ");
            System.out.println("===================================");
            System.out.println("1. Cadastrar Novo Produtor");
            System.out.println("2. Listar Produtores");
            System.out.println("3. Atualizar Produtor (por ID)");
            System.out.println("4. Deletar Produtor (por ID)");
            System.out.println("5. DELETAR TUDO (Limpar Ficheiro)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.println("\n--- Cadastro de Produtor ---");
                        List<Produtor> listaAtual = GerenciadorArquivos.listarProdutores();


                        int id = gerarProximoIdRecursivo(listaAtual, 0, 0);
                        System.out.println("ID Gerado: " + id);


                        System.out.print("Nome: ");
                        String nomeDigitado = scanner.nextLine().trim();

                        if (!isNomeValido(nomeDigitado)) {
                            System.out.println("[Erro] Nome inválido! Digite apenas letras sem números.");
                            break;
                        }
                        String nomeFinal = formatarNome(nomeDigitado);


                        System.out.print("Telefone (somente 10 ou 11 números com DDD): ");
                        String telefone = scanner.nextLine().trim();

                        if (!isTelefoneValido(telefone)) {
                            System.out.println("[Erro] Telefone inválido! Deve conter 10 ou 11 dígitos numéricos.");
                            break;
                        }

                        System.out.print("CPF (somente 11 números): ");
                        String cpf = scanner.nextLine().trim();

                        if (!isCpfValido(cpf)) {
                            System.out.println("[Erro] CPF inválido! Deve conter exatamente 11 dígitos numéricos.");
                            break;
                        }

                        boolean cpfExiste = false;
                        for (Produtor p : listaAtual) {
                            if (p.getCpf().equals(cpf)) {
                                cpfExiste = true;
                                break;
                            }
                        }

                        if (cpfExiste) {
                            System.out.println("[Erro] Já existe um produtor com este CPF!");
                            break;
                        }

                        Produtor novoProdutor = new Produtor(id, nomeFinal, telefone, cpf);
                        GerenciadorArquivos.salvarProdutor(novoProdutor);
                    }

                    case 2 -> {
                        System.out.println("\n--- Lista de Produtores ---");
                        List<Produtor> lista = GerenciadorArquivos.listarProdutores();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum produtor cadastrado no ficheiro.");
                        } else {
                            for (Produtor p : lista) {
                                System.out.println("ID: " + p.getIdProdutor() +
                                        " | Nome: " + p.getNome() +
                                        " | Tel: " + p.getTelefone() +
                                        " | CPF: " + p.getCpf());
                            }
                        }
                    }

                    case 3 -> {
                        System.out.println("\n--- Atualizar Produtor ---");
                        System.out.print("Digite o ID do produtor a alterar: ");
                        int idBusca = scanner.nextInt();
                        scanner.nextLine();

                        List<Produtor> lista = GerenciadorArquivos.listarProdutores();
                        boolean encontrado = false;

                        for (Produtor p : lista) {
                            if (p.getIdProdutor() == idBusca) {
                                System.out.print("Novo Nome (atual: " + p.getNome() + "): ");
                                String novoNome = scanner.nextLine().trim();
                                if (isNomeValido(novoNome)) {
                                    p.setNome(formatarNome(novoNome));
                                }

                                System.out.print("Novo Telefone (atual: " + p.getTelefone() + "): ");
                                String novoTel = scanner.nextLine().trim();
                                if (isTelefoneValido(novoTel)) {
                                    p.setTelefone(novoTel);
                                }

                                encontrado = true;
                                break;
                            }
                        }

                        if (encontrado) {
                            GerenciadorArquivos.reescreverArquivo(lista);
                            System.out.println("[Sucesso] Dados atualizados no ficheiro!");
                        } else {
                            System.out.println("[Aviso] ID não encontrado.");
                        }
                    }

                    case 4 -> {
                        System.out.println("\n--- Deletar Produtor ---");
                        System.out.print("Digite o ID do produtor a remover: ");
                        int idBusca = scanner.nextInt();
                        scanner.nextLine();

                        List<Produtor> lista = GerenciadorArquivos.listarProdutores();
                        boolean removido = lista.removeIf(p -> p.getIdProdutor() == idBusca);

                        if (removido) {
                            GerenciadorArquivos.reescreverArquivo(lista);
                            System.out.println("[Sucesso] Produtor removido do ficheiro!");
                        } else {
                            System.out.println("[Aviso] ID " + idBusca + " não encontrado.");
                        }
                    }

                    case 5 -> {
                        System.out.println("\n--- LIMPAR FICHEIRO COMPLETO ---");
                        System.out.print("Tem certeza que deseja apagar TODOS os produtores? (S/N): ");
                        String confirma = scanner.nextLine();
                        if (confirma.equalsIgnoreCase("S")) {
                            GerenciadorArquivos.limparTudo();
                        } else {
                            System.out.println("Operação cancelada.");
                        }
                    }

                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("[Erro] Opção inválida!");
                }

            } catch (InputMismatchException e) {
                System.out.println("[Atenção] Digite apenas números válidos.");
                scanner.nextLine();
                opcao = -1;
            }

        } while (opcao != 0);
    }
}