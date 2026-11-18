package gerenciador;

import cadastro.Piquete;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorPiquetes {
    private static final String CAMINHO_ARQUIVO = "piquetes.txt";

    public static void salvarPiquete(Piquete piquete) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            pw.println(piquete.toCSV());
            System.out.println("[Sucesso] Piquete salvo no arquivo!");

        } catch (IOException e) {
            System.out.println("[Erro] Não foi possível salvar o piquete: " + e.getMessage());
        }
    }

    public static List<Piquete> listarPiquetes() {
        List<Piquete> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) return lista;

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    int id = Integer.parseInt(dados[0].trim());
                    String ident = dados[1].trim();
                    double area = Double.parseDouble(dados[2].trim());
                    int capacidade = Integer.parseInt(dados[3].trim());

                    Piquete p = new Piquete(id, ident, area, capacidade);
                    lista.add(p);
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("[Erro] Falha ao ler o arquivo de piquetes: " + e.getMessage());
        }

        return lista;
    }

    public static void reescreverArquivo(List<Piquete> lista) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, false))) {
            for (Piquete p : lista) {
                pw.println(p.toCSV());
            }
        } catch (IOException e) {
            System.out.println("[Erro] Falha ao atualizar arquivo de piquetes: " + e.getMessage());
        }
    }

    public static void limparTudo() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (FileWriter fw = new FileWriter(arquivo, false)) {
            System.out.println("[Sucesso] Todos os piquetes foram deletados!");
        } catch (IOException e) {
            System.out.println("[Erro] Falha ao limpar o arquivo: " + e.getMessage());
        }
    }
}