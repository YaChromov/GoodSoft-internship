package solids;

import base.Solid;

public class Cylinder extends Solid {
    private double radius;
    private double height;

    public Cylinder(double radius, double height) {
        super("цилиндр");
        this.radius = radius;
        this.height = height;
    }

    @Override
    public double volume() {
        return Math.PI * radius * radius * height;
    }
}