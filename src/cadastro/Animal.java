package cadastro;


public class Animal {
    private int id_animal;
    private String raca;
    private double peso;



    public Animal(int id_animal, String raca, double peso){
        this.id_animal = id_animal;
        this.peso = peso;
        this.raca = raca;


    }

    public int  getId_Animal(){
        return id_animal;
    }
    public void setId_animal(int id_animal){
        this.id_animal = id_animal;
    }
    public String getRaca(){
        return raca;
    }
    public void setRaca(String raca){
        this.raca = raca;
    }
    public double getPeso(){
        return peso;
    }
    public void setPeso(double peso){
        this.peso = peso;
    }
    public String toCSV(){
        return id_animal + ";" + raca + ";" + peso;
    }


}
