package solids;

import base.Solid;

public class Cube extends Solid {
    private double side;

    public Cube(double side) {
        super("куб");
        this.side = side;
    }

    @Override
    public double volume() {
        return Math.pow(side, 3);
    }
}