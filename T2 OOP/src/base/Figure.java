package base;

public abstract class Figure extends PrintableObject {
    protected String name;

    public Figure(String name) {
        this.name = name;
    }

    public abstract double area();
    public abstract double perimeter();

    @Override
    public String getInfo() {
        return String.format("это фигура - %s с периметром %.2f и площадью %.2f",
                name, perimeter(), area());
    }
}