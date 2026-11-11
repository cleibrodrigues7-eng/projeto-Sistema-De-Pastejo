package menu;



import java.util.Scanner;

public class MenuAnimal {
    public static void gerenciar(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR ANIMAIS ---");
            System.out.println("1. Cadastrar Novo Animal");
            System.out.println("2. Listar Animais");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("Escolha  uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.println("\n--- Cadastro Animal ---");
                    System.out.print("Número de Indentificação: ");
                    String id_animal = sc.nextLine();

                    System.out.print("Raça: ");
                    String raca = sc.nextLine();

                    System.out.print("Peso: ");
                    double peso = sc.nextDouble();
                }

                case 2 -> {
                }

                default -> System.out.println("Opção iválida!");
            }
        } while (opcao != 0);

    }

}
