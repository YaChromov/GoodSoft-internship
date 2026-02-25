package solids;

import base.Solid;

public class Cone extends Solid {
    private double radius;
    private double height;

    public Cone(double radius, double height) {
        super("конус");
        this.radius = radius;
        this.height = height;
    }

    @Override
    public double volume() {
        return (1.0 / 3.0) * Math.PI * radius * radius * height;
    }
}