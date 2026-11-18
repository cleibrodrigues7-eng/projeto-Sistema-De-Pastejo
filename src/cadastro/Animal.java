package cadastro;

import java.io.Serializable;

public class Animal implements Serializable {
    private int idAnimal;
    private String raca;
    private double peso;

    public Animal(int idAnimal, String raca, double peso) {
        this.idAnimal = idAnimal;
        this.raca = raca;
        this.peso = peso;
    }

    public int getIdAnimal() { return idAnimal; }
    public void setIdAnimal(int idAnimal) { this.idAnimal = idAnimal; }

    public String getRaca() {return raca;}
    public void setRaca(String raca) { this.raca = raca; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public String toCSV() {
        return idAnimal + ";" + raca + ";" + peso;
    }
}