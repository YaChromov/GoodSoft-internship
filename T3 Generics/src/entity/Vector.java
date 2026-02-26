package entity;

/**
 * Класс-сущность вектор в двумерном пространстве
 */
public class Vector {
    private double x;
    private double y;

    /**
     * Создает вектор с указанными координатами
     * @param x координата X
     * @param y координата Y
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }
}
