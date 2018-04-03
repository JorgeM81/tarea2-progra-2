package Domain;

public class Car {
    private String name;
    private int year;
    private float mileage;
    private boolean american;
    private int serie;
    
    public Car(){
        
    }

    public Car(String name, int year, float mileage, boolean american, int serie) {
        this.name = name;
        this.year = year;
        this.mileage = mileage;
        this.american = american;
        this.serie = serie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public boolean isAmerican() {
        return american;
    }

    public void setAmerican(boolean american) {
        this.american = american;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "Car{" + "name=" + name + ", year=" + year + ", mileage=" + mileage + ", american=" + american + ", serie=" + serie + '}';
    }
    public int sizeInBytes(){
        return String.valueOf(this.getYear()).length() * 4 + String.valueOf(this.getSerie()).length() * 4 + this.getName().length() *2+20;
    }
}
