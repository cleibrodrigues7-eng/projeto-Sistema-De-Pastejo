package gerenciador;

import cadastro.Rotacao;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorRotacao {
    private static final String CAMINHO_ARQUIVO = "rotacao_pasto.txt";

    public static void salvarRotacao(Rotacao rotacao) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            pw.println(rotacao.toCSV());
            System.out.println("[Sucesso] Registro de rotação salvo no arquivo!");

        } catch (IOException e) {
            System.out.println("[Erro] Não foi possível salvar a rotação: " + e.getMessage());
        }
    }

    public static List<Rotacao> listarRotacoes() {
        List<Rotacao> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) return lista;

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");
                if (dados.length == 5) {
                    int idRot = Integer.parseInt(dados[0].trim());
                    int idP = Integer.parseInt(dados[1].trim());
                    int idA = Integer.parseInt(dados[2].trim());
                    String dataE = dados[3].trim();
                    String dataS = dados[4].trim();

                    lista.add(new Rotacao(idRot, idP, idA, dataE, dataS));
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("[Erro] Falha ao ler o arquivo de rotações: " + e.getMessage());
        }

        return lista;
    }

    public static void reescreverArquivo(List<Rotacao> lista) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, false))) {
            for (Rotacao r : lista) {
                pw.println(r.toCSV());
            }
        } catch (IOException e) {
            System.out.println("[Erro] Falha ao atualizar arquivo de rotações: " + e.getMessage());
        }
    }

    public static void limparTudo() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (FileWriter fw = new FileWriter(arquivo, false)) {
            System.out.println("[Sucesso] Todos os registros de rotação foram deletados!");
        } catch (IOException e) {
            System.out.println("[Erro] Falha ao limpar o arquivo: " + e.getMessage());
        }
    }
}