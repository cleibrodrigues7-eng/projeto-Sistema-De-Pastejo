package menu;



import java.util.Scanner;
public class MenuProdutor {
    public static void gerenciar(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n===================================");
            System.out.println("   GERENCIAR PRODUTORES  ");
            System.out.println("===================================");
            System.out.println("1. Cadastrar Novo Produtor");
            System.out.println("2. Lista Produtores");
            System.out.println("3. Atualizar Produtor");
            System.out.println("4. Deletar Produtor");
            System.out.println("0. Voltar ao Menu Principal");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao){
                case 1 -> {
                }

                case 2 -> {
                }

                case 3 -> {
                }

                case 4 -> {
                }

                case 0 -> {
                }
                default -> System.out.println("Voltando");

            }

        } while (opcao != 0);
    }

}
