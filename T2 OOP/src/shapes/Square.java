package shapes;

import base.Figure;

public class Square extends Figure {
    private double side;

    public Square(double side) {
        super("квадрат");
        this.side = side;
    }

    private boolean isValid() {
        return side > 0;
    }

    @Override
    public double area() {
        return side * side;
    }

    @Override
    public double perimeter() {
        return 4 * side;
    }
}
