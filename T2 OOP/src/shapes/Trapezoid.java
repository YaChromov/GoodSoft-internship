package shapes;

import base.Figure;

public class Trapezoid extends Figure {
    private double base1;  // верхнее основание
    private double base2;  // нижнее основание
    private double side1;  // левая боковая сторона
    private double side2;  // правая боковая сторона
    private double height; // высота

    public Trapezoid(double base1, double base2, double side1, double side2, double height) {
        super("трапеция");
        this.base1 = base1;
        this.base2 = base2;
        this.side1 = side1;
        this.side2 = side2;
        this.height = height;
    }

    @Override
    public double area() {
        return (base1 + base2) * height / 2;
    }

    @Override
    public double perimeter() {
        return base1 + base2 + side1 + side2;
    }
}