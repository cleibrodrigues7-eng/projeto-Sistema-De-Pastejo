package menu;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===================================");
            System.out.println("    SISTEMA DE PASTEJO ROTACIONAL");
            System.out.println("=====================================");
            System.out.println("1. Gerenciar Produtores (Cadastrar, Listar, etc.)");
            System.out.println("2. Gerenciar Piquetes");
            System.out.println("3. Gerenciar Vacas / Animais");
            System.out.println("4. Controle de Rotação de Pasto");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção acima: ");

            try {
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        MenuProdutor.gerenciar(sc);
                        break;
                    case 2:
                        MenuPiquete.gerenciar(sc);
                        break;
                    case 3:
                        MenuAnimal.gerenciar(sc);
                        break;
                    case 4:
                       MenuRotacao.gerenciar(sc);
                        break;

                    case 0:
                        System.out.println("\nSaindo....");
                        break;
                    default:
                        System.out.println("\n[Erro] Opção inválida! Escolha entre 0 e 4.");
                }

            } catch (InputMismatchException e) {
                System.out.println("\n[Atenção] Por favor, digite apenas números válidos!");
                sc.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);

    }

}