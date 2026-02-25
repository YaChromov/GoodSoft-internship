package shapes;

import base.Figure;

public class Triangle extends Figure {
    private double a, b, c;

    public Triangle(double a, double b, double c) {
        super("треугольник");
        this.a = a;
        this.b = b;
        this.c = c;

        if (!isValid()) {
            throw new IllegalArgumentException("Треугольник с такими сторонами не существует");
        }
    }

    private boolean isValid() {
        return a > 0 && b > 0 && c > 0 && (a + b > c) && (a + c > b) && (b + c > a);
    }

    @Override
    public double perimeter() {
        return a + b + c;
    }

    @Override
    public double area() {
        double p = perimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}