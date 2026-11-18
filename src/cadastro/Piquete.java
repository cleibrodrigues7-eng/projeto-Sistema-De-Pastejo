package cadastro;

import java.io.Serializable;

public class Piquete implements Serializable {
    private int idPiquete;
    private String identificacao;
    private double areaHectares;
    private int capacidadeAnimais;

    public Piquete(int idPiquete, String identificacao, double areaHectares, int capacidadeAnimais) {
        this.idPiquete = idPiquete;
        this.identificacao = identificacao;
        this.areaHectares = areaHectares;
        this.capacidadeAnimais = capacidadeAnimais;
    }

    public int getIdPiquete() {
        return idPiquete;
    }

    public void setIdPiquete(int idPiquete) {
        this.idPiquete = idPiquete;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public double getAreaHectares() {
        return areaHectares;
    }

    public void setAreaHectares(double areaHectares) {
        this.areaHectares = areaHectares;
    }

    public int getCapacidadeAnimais() {
        return capacidadeAnimais;
    }

    public void setCapacidadeAnimais(int capacidadeAnimais) {
        this.capacidadeAnimais = capacidadeAnimais;
    }

    public String toCSV() {
        return idPiquete + ";" + identificacao + ";" + areaHectares + ";" + capacidadeAnimais;
    }
}