package base;

public abstract class Solid extends PrintableObject {

    public Solid(String name) {
        this.name = name;
    }

    public abstract double volume();


    @Override
    public String getInfo() {
        return String.format("это тело - %s с объемом %.2f",
                name, volume());
    }
}