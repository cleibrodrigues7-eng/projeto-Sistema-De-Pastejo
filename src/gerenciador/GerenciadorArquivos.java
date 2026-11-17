package gerenciador;
import cadastro.Produtor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class GerenciadorArquivos {
    public static final String CAMINHO_ARQUIVO = "produtores.txt";

    public static void salvarProdutor(Produtor produtor) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            pw.println(produtor.toCSV());
            System.out.println("[Sucesso] Produtor salvo no arquivo!");

        } catch (IOException e) {
            System.out.println("[Erro] Não foi possível salvar: " + e.getMessage());
        }
    }

    public static List<Produtor> listarProdutores() {
        List<Produtor> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) return lista;

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    int id = Integer.parseInt(dados[0]);
                    Produtor p = new Produtor(id, dados[1], dados[2], dados[3]);
                    lista.add(p);
                }
            }

        } catch (IOException e) {
            System.out.println("[Erro] Falha ao ler o arquivo: " + e.getMessage());
        }

        return lista;
    }

    public static void reescreverArquivo(List<Produtor> lista) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, false))) {
            for (Produtor p : lista) {
                pw.println(p.toCSV());
            }
        } catch (IOException e) {
            System.out.println("[Erro] Falha ao atualizar arquivo: " + e.getMessage());
        }
    }
    public static void limparTudo() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        try (FileWriter fw = new FileWriter(arquivo, false)) {

            System.out.println("[Sucesso] Todos os registros e informações foram deletados!");
        } catch (IOException e) {
            System.out.println("[Erro] Falha ao limpar o arquivo: " + e.getMessage());
        }
    }
}
