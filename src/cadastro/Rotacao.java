package cadastro;

import java.io.Serializable;

public class Rotacao implements Serializable {
    private int idRotacao;
    private int idPiquete;
    private int idAnimal;
    private String dataEntrada;
    private String dataSaida;

    public Rotacao(int idRotacao, int idPiquete, int idAnimal, String dataEntrada, String dataSaida) {
        this.idRotacao = idRotacao;
        this.idPiquete = idPiquete;
        this.idAnimal = idAnimal;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
    }

    public int getIdRotacao() { return idRotacao; }
    public void setIdRotacao(int idRotacao) { this.idRotacao = idRotacao; }

    public int getIdPiquete() { return idPiquete; }
    public void setIdPiquete(int idPiquete) { this.idPiquete = idPiquete; }

    public int getIdAnimal() { return idAnimal; }
    public void setIdAnimal(int idAnimal) { this.idAnimal = idAnimal; }

    public String getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(String dataEntrada) { this.dataEntrada = dataEntrada; }

    public String getDataSaida() { return dataSaida; }
    public void setDataSaida(String dataSaida) { this.dataSaida = dataSaida; }

    public String toCSV() {
        return idRotacao + ";" + idPiquete + ";" + idAnimal + ";" + dataEntrada + ";" + dataSaida;
    }
}