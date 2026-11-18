package menu;

import cadastro.Animal;
import gerenciador.GerenciadorAnimais;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuAnimal {

    // RECURSIVIDADE: Geração automática de ID do animal
    private static int gerarProximoIdRecursivo(List<Animal> lista, int indice, int maiorIdAtual) {
        if (indice >= lista.size()) {
            return maiorIdAtual + 1;
        }
        int idAtual = lista.get(indice).getIdAnimal();
        int novoMaior = Math.max(maiorIdAtual, idAtual);
        return gerarProximoIdRecursivo(lista, indice + 1, novoMaior);
    }

    // Formata nomes de raças (ex: "nelore" -> "Nelore")
    private static String formatarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) return "";
        String[] palavras = texto.trim().split("\\s+");
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

    public static void gerenciar(Scanner sc) {
        // FORÇA O SCANNER A ACEITAR PONTO (.) PARA NÚMEROS DECIMAIS (ex: 450.5)
        sc.useLocale(Locale.US);
        int opcao = -1;

        do {
            System.out.println("\n===================================");
            System.out.println("        GERENCIAR ANIMAIS          ");
            System.out.println("===================================");
            System.out.println("1. Cadastrar Novo Animal");
            System.out.println("2. Listar Animais");
            System.out.println("3. Atualizar Animal (por ID)");
            System.out.println("4. Deletar Animal (por ID)");
            System.out.println("5. DELETAR TUDO (Limpar Arquivo)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = sc.nextInt();
                sc.nextLine(); // Limpa o buffer

                switch (opcao) {
                    case 1 -> {
                        System.out.println("\n--- Cadastro de Animal ---");
                        List<Animal> listaAtual = GerenciadorAnimais.listarAnimais();

                        // ID gerado via recursividade (atendendo à exigência da professora)
                        int id = gerarProximoIdRecursivo(listaAtual, 0, 0);
                        System.out.println("ID de Identificação Gerado: " + id);

                        System.out.print("Raça (ex: Nelore, Angus): ");
                        String racaDigitada = sc.nextLine().trim();

                        if (racaDigitada.isEmpty()) {
                            System.out.println("[Erro] A raça não pode ficar em branco!");
                            break;
                        }
                        String racaFinal = formatarTexto(racaDigitada);

                        System.out.print("Peso em kg (ex: 450.5): ");
                        double peso = sc.nextDouble();
                        sc.nextLine(); // Limpa o buffer após o nextDouble

                        if (peso <= 0) {
                            System.out.println("[Erro] O peso deve ser maior que zero!");
                            break;
                        }

                        // Salva o animal
                        Animal novoAnimal = new Animal(id, racaFinal, peso);
                        GerenciadorAnimais.salvarAnimal(novoAnimal);
                    }

                    case 2 -> {
                        System.out.println("\n--- Lista de Animais ---");
                        List<Animal> lista = GerenciadorAnimais.listarAnimais();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum animal cadastrado no arquivo.");
                        } else {
                            for (Animal a : lista) {
                                System.out.println("ID: " + a.getIdAnimal() +
                                        " | Raça: " + a.getRaca() +
                                        " | Peso: " + a.getPeso() + " kg");
                            }
                        }
                    }

                    case 3 -> {
                        System.out.println("\n--- Atualizar Animal ---");
                        System.out.print("Digite o ID do animal que deseja alterar: ");
                        int idBusca = sc.nextInt();
                        sc.nextLine();

                        List<Animal> lista = GerenciadorAnimais.listarAnimais();
                        boolean encontrado = false;

                        for (Animal a : lista) {
                            if (a.getIdAnimal() == idBusca) {
                                System.out.print("Nova Raça (atual: " + a.getRaca() + "): ");
                                String novaRaca = sc.nextLine().trim();
                                if (!novaRaca.isEmpty()) {
                                    a.setRaca(formatarTexto(novaRaca));
                                }

                                System.out.print("Novo Peso (atual: " + a.getPeso() + "): ");
                                double novoPeso = sc.nextDouble();
                                sc.nextLine();
                                if (novoPeso > 0) a.setPeso(novoPeso);

                                encontrado = true;
                                break;
                            }
                        }

                        if (encontrado) {
                            GerenciadorAnimais.reescreverArquivo(lista);
                            System.out.println("[Sucesso] Dados do animal atualizados!");
                        } else {
                            System.out.println("[Aviso] Animal com o ID " + idBusca + " não encontrado.");
                        }
                    }

                    case 4 -> {
                        System.out.println("\n--- Deletar Animal ---");
                        System.out.print("Digite o ID do animal a remover: ");
                        int idBusca = sc.nextInt();
                        sc.nextLine();

                        List<Animal> lista = GerenciadorAnimais.listarAnimais();
                        boolean removido = lista.removeIf(a -> a.getIdAnimal() == idBusca);

                        if (removido) {
                            GerenciadorAnimais.reescreverArquivo(lista);
                            System.out.println("[Sucesso] Animal removido do arquivo!");
                        } else {
                            System.out.println("[Aviso] ID " + idBusca + " não encontrado.");
                        }
                    }

                    case 5 -> {
                        System.out.println("\n--- LIMPAR BANCO DE ANIMAIS ---");
                        System.out.print("Tem certeza que deseja apagar TODOS os animais? (S/N): ");
                        String confirma = sc.nextLine();
                        if (confirma.equalsIgnoreCase("S")) {
                            GerenciadorAnimais.limparTudo();
                        } else {
                            System.out.println("Operação cancelada.");
                        }
                    }

                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("[Erro] Opção inválida!");
                }

            } catch (InputMismatchException e) {
                System.out.println("[Atenção] Por favor, digite valores válidos (números onde exigido)!");
                sc.nextLine(); // Limpa a entrada incorreta do usuário
                opcao = -1;
            }

        } while (opcao != 0);
    }
}