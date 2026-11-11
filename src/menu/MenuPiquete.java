package menu;




import java.util.Scanner;
public class MenuPiquete {
    public static void gerenciar(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR PIQUETE ---");
            System.out.println("1.  Cadastrar Novo Piquete");
            System.out.println("2. Listar Piquetes");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("Escolha uma opção");

            opcao = sc.nextInt();
            sc.nextLine();


            switch (opcao) {
                case 1 -> {

                }

                case 2 -> {
                }

                case 0 -> {
                }

                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

    }

}
