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

    private boolean isValid() {
        final double ERROR_COEFF = 0.0001 // погрешность вычислений с плавающей запятой
                ;
        if (base1 < 0 || base2 < 0 || side1 < 0 || side2 < 0 || height < 0) {
            return false;
        }

        if (height > side1 || height > side2) {
            return false;
        }

        double projection1 = Math.sqrt(side1 * side1 - height * height);
        double projection2 = Math.sqrt(side2 * side2 - height * height);

        double baseDifference = Math.abs(base2 - base1);

        return projection1 + projection2 >= baseDifference - ERROR_COEFF;
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