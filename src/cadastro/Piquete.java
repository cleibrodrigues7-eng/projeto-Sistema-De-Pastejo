package cadastro;

import java.io.Serializable;

public class Piquete implements Serializable {
    private String idPiquete;
    private String capim;
    private double tamanho;

    public Piquete(String idPiquete, String capim, double tamanho) {
        this.idPiquete = idPiquete;
        this.capim = capim;
        this.tamanho = tamanho;
    }

    public String getCapim() {
        return capim;
    }

    public void setCapim(String capim) {
        this.capim = capim;
    }

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    public String getIdPiquete() {
        return idPiquete;
    }

    public void setIdPiquete(String idPiquete) {
        this.idPiquete = idPiquete;
    }

    public String toCSV() {
        return idPiquete + ";" + capim + ";" + tamanho;
    }
}