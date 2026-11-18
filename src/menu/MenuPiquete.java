package menu;

import cadastro.Piquete;
import gerenciador.GerenciadorPiquetes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuPiquete {

    private static int gerarProximoIdRecursivo(List<Piquete> lista, int indice, int maiorIdAtual) {
        if (indice >= lista.size()) {
            return maiorIdAtual + 1;
        }
        int idAtual = lista.get(indice).getIdPiquete();
        int novoMaior = Math.max(maiorIdAtual, idAtual);
        return gerarProximoIdRecursivo(lista, indice + 1, novoMaior);
    }

    private static String formatarIdentificacao(String texto) {
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
        sc.useLocale(Locale.US);
        int opcao = -1;

        do {
            System.out.println("\n===================================");
            System.out.println("        GERENCIAR PIQUETE          ");
            System.out.println("===================================");
            System.out.println("1. Cadastrar Novo Piquete");
            System.out.println("2. Listar Piquetes");
            System.out.println("3. Atualizar Piquete (por ID)");
            System.out.println("4. Deletar Piquete (por ID)");
            System.out.println("5. DELETAR TUDO (Limpar Arquivo)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.println("\n--- Cadastro de Piquete ---");
                        List<Piquete> listaAtual = GerenciadorPiquetes.listarPiquetes();


                        int id = gerarProximoIdRecursivo(listaAtual, 0, 0);
                        System.out.println("ID Gerado: " + id);


                        System.out.print("Identificação (ex: Piquete 01): ");
                        String identDigitada = sc.nextLine().trim();

                        if (identDigitada.isEmpty()) {
                            System.out.println("[Erro] A identificação do piquete não pode ficar em branco!");
                            break;
                        }
                        String identFinal = formatarIdentificacao(identDigitada);


                        System.out.print("Área do piquete (em hectares, ex: 2.5): ");
                        double area = sc.nextDouble();
                        sc.nextLine();

                        if (area <= 0) {
                            System.out.println("[Erro] A área deve ser maior que zero!");
                            break;
                        }


                        System.out.print("Lotação máxima suportada (cabeças por hectare, ex: 2): ");
                        double lotacaoPorHectare = sc.nextDouble();
                        sc.nextLine();

                        if (lotacaoPorHectare <= 0) {
                            System.out.println("[Erro] A lotação deve ser maior que zero!");
                            break;
                        }


                        int capacidadeTotal = (int) Math.round(area * lotacaoPorHectare);

                        System.out.println("\n-------------------------------------------");
                        System.out.println("-> Capacidade Calculada: " + capacidadeTotal + " cabeças de gado ("
                                + area + " ha x " + lotacaoPorHectare + " cab/ha)");
                        System.out.println("-------------------------------------------");


                        Piquete novoPiquete = new Piquete(id, identFinal, area, capacidadeTotal);
                        GerenciadorPiquetes.salvarPiquete(novoPiquete);
                    }

                    case 2 -> {
                        System.out.println("\n--- Lista de Piquetes ---");
                        List<Piquete> lista = GerenciadorPiquetes.listarPiquetes();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum piquete cadastrado no arquivo.");
                        } else {
                            for (Piquete p : lista) {
                                System.out.println("ID: " + p.getIdPiquete() +
                                        " | Nome: " + p.getIdentificacao() +
                                        " | Área: " + p.getAreaHectares() + " ha" +
                                        " | Cap: " + p.getCapacidadeAnimais() + " animais");
                            }
                        }
                    }

                    case 3 -> {
                        System.out.println("\n--- Atualizar Piquete ---");
                        System.out.print("Digite o ID do piquete que deseja alterar: ");
                        int idBusca = sc.nextInt();
                        sc.nextLine();

                        List<Piquete> lista = GerenciadorPiquetes.listarPiquetes();
                        boolean encontrado = false;

                        for (Piquete p : lista) {
                            if (p.getIdPiquete() == idBusca) {
                                System.out.print("Nova Identificação (atual: " + p.getIdentificacao() + "): ");
                                String novaIdent = sc.nextLine().trim();
                                if (!novaIdent.isEmpty()) {
                                    p.setIdentificacao(formatarIdentificacao(novaIdent));
                                }

                                System.out.print("Nova Área em hectares (atual: " + p.getAreaHectares() + "): ");
                                double novaArea = sc.nextDouble();
                                sc.nextLine();
                                if (novaArea > 0) p.setAreaHectares(novaArea);

                                System.out.print("Nova Capacidade (atual: " + p.getCapacidadeAnimais() + "): ");
                                int novaCap = sc.nextInt();
                                sc.nextLine();
                                if (novaCap > 0) p.setCapacidadeAnimais(novaCap);

                                encontrado = true;
                                break;
                            }
                        }

                        if (encontrado) {
                            GerenciadorPiquetes.reescreverArquivo(lista);
                            System.out.println("[Sucesso] Piquete atualizado no arquivo!");
                        } else {
                            System.out.println("[Aviso] Piquete com o ID " + idBusca + " não foi encontrado.");
                        }
                    }

                    case 4 -> {
                        System.out.println("\n--- Deletar Piquete ---");
                        System.out.print("Digite o ID do piquete a remover: ");
                        int idBusca = sc.nextInt();
                        sc.nextLine();

                        List<Piquete> lista = GerenciadorPiquetes.listarPiquetes();
                        boolean removido = lista.removeIf(p -> p.getIdPiquete() == idBusca);

                        if (removido) {
                            GerenciadorPiquetes.reescreverArquivo(lista);
                            System.out.println("[Sucesso] Piquete removido do arquivo!");
                        } else {
                            System.out.println("[Aviso] ID " + idBusca + " não encontrado.");
                        }
                    }

                    case 5 -> {
                        System.out.println("\n--- LIMPAR BANCO DE PIQUETES ---");
                        System.out.print("Tem certeza que deseja apagar TODOS os piquetes? (S/N): ");
                        String confirma = sc.nextLine();
                        if (confirma.equalsIgnoreCase("S")) {
                            GerenciadorPiquetes.limparTudo();
                        } else {
                            System.out.println("Operação cancelada.");
                        }
                    }

                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("[Erro] Opção inválida!");
                }

            } catch (InputMismatchException e) {
                System.out.println("[Atenção] Por favor, digite valores válidos!");
                sc.nextLine();
                opcao = -1;
            }

        } while (opcao != 0);
    }
}