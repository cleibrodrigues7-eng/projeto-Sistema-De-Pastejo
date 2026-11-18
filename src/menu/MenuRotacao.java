package menu;

import cadastro.Rotacao;
import gerenciador.GerenciadorRotacao;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuRotacao {

    private static int gerarProximoIdRecursivo(List<Rotacao> lista, int indice, int maiorIdAtual) {
        if (indice >= lista.size()) {
            return maiorIdAtual + 1;
        }
        int idAtual = lista.get(indice).getIdRotacao();
        int novoMaior = Math.max(maiorIdAtual, idAtual);
        return gerarProximoIdRecursivo(lista, indice + 1, novoMaior);
    }

    private static boolean isDataValida(String data) {
        return data != null && data.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$");
    }

    public static void gerenciar(Scanner sc) {
        int opcao = -1;

        do {
            System.out.println("\n===================================");
            System.out.println("   CONTROLE DE ROTAÇÃO DE PASTO    ");
            System.out.println("===================================");
            System.out.println("1. Registrar Nova Rotação");
            System.out.println("2. Listar Histórico de Rotações");
            System.out.println("3. Atualizar Rotação (por ID)");
            System.out.println("4. Deletar Rotação (por ID)");
            System.out.println("5. DELETAR TUDO (Limpar Arquivo)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.println("\n--- Nova Rotação ---");
                        List<Rotacao> listaAtual = GerenciadorRotacao.listarRotacoes();


                        int idRotacao = gerarProximoIdRecursivo(listaAtual, 0, 0);
                        System.out.println("ID da Rotação Gerado: " + idRotacao);


                        System.out.print("ID do Piquete de destino: ");
                        int idPiquete = sc.nextInt();
                        sc.nextLine();


                        System.out.print("ID do Animal a ser movido: ");
                        int idAnimal = sc.nextInt();
                        sc.nextLine();


                        System.out.print("Data de Entrada (DD/MM/AAAA): ");
                        String dataEntrada = sc.nextLine().trim();

                        if (!isDataValida(dataEntrada)) {
                            System.out.println("[Erro] Data inválida! Use o formato DD/MM/AAAA.");
                            break;
                        }


                        System.out.print("Data de Saída (DD/MM/AAAA) ou digite 'Atual' se ainda está no pasto: ");
                        String dataSaida = sc.nextLine().trim();

                        if (!dataSaida.equalsIgnoreCase("Atual") && !isDataValida(dataSaida)) {
                            System.out.println("[Erro] Data inválida! Use o formato DD/MM/AAAA ou 'Atual'.");
                            break;
                        }

                        Rotacao novaRotacao = new Rotacao(idRotacao, idPiquete, idAnimal, dataEntrada, dataSaida);
                        GerenciadorRotacao.salvarRotacao(novaRotacao);
                    }

                    case 2 -> {
                        System.out.println("\n--- Histórico de Rotações ---");
                        List<Rotacao> lista = GerenciadorRotacao.listarRotacoes();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhuma rotação registrada no arquivo.");
                        } else {
                            for (Rotacao r : lista) {
                                System.out.println("ID Rotação: " + r.getIdRotacao() +
                                        " | Piquete: " + r.getIdPiquete() +
                                        " | Animal: " + r.getIdAnimal() +
                                        " | Entrada: " + r.getDataEntrada() +
                                        " | Saída: " + r.getDataSaida());
                            }
                        }
                    }

                    case 3 -> {
                        System.out.println("\n--- Atualizar Rotação ---");
                        System.out.print("Digite o ID da rotação que deseja alterar: ");
                        int idBusca = sc.nextInt();
                        sc.nextLine();

                        List<Rotacao> lista = GerenciadorRotacao.listarRotacoes();
                        boolean encontrado = false;

                        for (Rotacao r : lista) {
                            if (r.getIdRotacao() == idBusca) {
                                System.out.print("Nova Data de Saída (atual: " + r.getDataSaida() + ") [DD/MM/AAAA]: ");
                                String novaSaida = sc.nextLine().trim();

                                if (isDataValida(novaSaida) || novaSaida.equalsIgnoreCase("Atual")) {
                                    r.setDataSaida(novaSaida);
                                    encontrado = true;
                                } else {
                                    System.out.println("[Erro] Data inválida! A alteração foi cancelada.");
                                    encontrado = true; // Para não cair no aviso de ID não encontrado, mas não salva.
                                    break;
                                }
                                break;
                            }
                        }

                        if (encontrado) {
                            GerenciadorRotacao.reescreverArquivo(lista);
                            System.out.println("[Sucesso] Rotação atualizada!");
                        } else {
                            System.out.println("[Aviso] Rotação com o ID " + idBusca + " não encontrada.");
                        }
                    }

                    case 4 -> {
                        System.out.println("\n--- Deletar Rotação ---");
                        System.out.print("Digite o ID da rotação a remover: ");
                        int idBusca = sc.nextInt();
                        sc.nextLine();

                        List<Rotacao> lista = GerenciadorRotacao.listarRotacoes();
                        boolean removido = lista.removeIf(r -> r.getIdRotacao() == idBusca);

                        if (removido) {
                            GerenciadorRotacao.reescreverArquivo(lista);
                            System.out.println("[Sucesso] Rotação removida do histórico!");
                        } else {
                            System.out.println("[Aviso] ID " + idBusca + " não encontrado.");
                        }
                    }

                    case 5 -> {
                        System.out.println("\n--- LIMPAR HISTÓRICO DE ROTAÇÕES ---");
                        System.out.print("Tem certeza que deseja apagar TODAS as rotações? (S/N): ");
                        String confirma = sc.nextLine();
                        if (confirma.equalsIgnoreCase("S")) {
                            GerenciadorRotacao.limparTudo();
                        } else {
                            System.out.println("Operação cancelada.");
                        }
                    }

                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("[Erro] Opção inválida!");
                }

            } catch (InputMismatchException e) {
                System.out.println("[Atenção] Por favor, digite valores numéricos válidos para IDs e Opções!");
                sc.nextLine();
                opcao = -1;
            }

        } while (opcao != 0);
    }
}