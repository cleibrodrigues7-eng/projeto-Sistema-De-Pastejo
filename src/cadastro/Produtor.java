package cadastro;

import java.io.Serializable;

public class Produtor implements Serializable {
    private int idProdutor;
    private String nome;
    private String telefone;
    private String cpf;

    public Produtor(int idProdutor, String nome, String telefone, String cpf) {
        this.idProdutor = idProdutor;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    public int getIdProdutor() {
        return idProdutor;
    }

    public void setIdProdutor(int idProdutor) {
        this.idProdutor = idProdutor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // CORRETO: Salva apenas o valor numérico de idProdutor
    public String toCSV() {
        return idProdutor + ";" + nome + ";" + telefone + ";" + cpf;
    }
}