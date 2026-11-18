package gerenciador;

import cadastro.Animal;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorAnimais {
    private static final String CAMINHO_ARQUIVO = "animais.txt";

    public static void salvarAnimal(Animal animal) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            pw.println(animal.toCSV());
            System.out.println("[Sucesso] Animal salvo no arquivo!");

        } catch (IOException e) {
            System.out.println("[Erro] Não foi possível salvar o animal: " + e.getMessage());
        }
    }

    public static List<Animal> listarAnimais() {
        List<Animal> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) return lista;

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    int id = Integer.parseInt(dados[0].trim());
                    String raca = dados[1].trim();
                    double peso = Double.parseDouble(dados[2].trim());

                    lista.add(new Animal(id, raca, peso));
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("[Erro] Falha ao ler o arquivo de animais: " + e.getMessage());
        }

        return lista;
    }

    public static void reescreverArquivo(List<Animal> lista) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, false))) {
            for (Animal a : lista) {
                pw.println(a.toCSV());
            }
        } catch (IOException e) {
            System.out.println("[Erro] Falha ao atualizar arquivo: " + e.getMessage());
        }
    }

    public static void limparTudo() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (FileWriter fw = new FileWriter(arquivo, false)) {
            System.out.println("[Sucesso] Todos os animais foram deletados do banco de dados!");
        } catch (IOException e) {
            System.out.println("[Erro] Falha ao limpar o arquivo: " + e.getMessage());
        }
    }
}