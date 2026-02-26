package impl;

import entity.Vector;
import interfaces.ArithmeticOperations;

/**
 * Реализация арифметических операций над векторами.
 *
 * <p>Класс обеспечивает выполнение арифметических операций над векторами </p>
 *
 * @version 1.0
 * @see ArithmeticOperations
 * @see Vector
 */
public class VectorArithmetic implements ArithmeticOperations<Vector> {

    /**
     * Выполняет сложение двух векторов.
     * Результатом является новый вектор, координаты которого равны сумме
     * соответствующих координат исходных векторов.
     *
     * @param a первый вектор
     * @param b второй вектор
     * @return новый вектор - результат сложения a + b
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Vector add(Vector a, Vector b) {
        validateVectors(a, b);

        return new Vector(
                a.getX() + b.getX(),
                a.getY() + b.getY()
        );
    }

    /**
     * Выполняет вычитание двух векторов.
     * Результатом является новый вектор, координаты которого равны разности
     * соответствующих координат исходных векторов (a - b).
     *
     * @param a первый вектор (уменьшаемое)
     * @param b второй вектор (вычитаемое)
     * @return новый вектор - результат вычитания a - b
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Vector subtract(Vector a, Vector b) {
        validateVectors(a, b);

        return new Vector(
                a.getX() - b.getX(),
                a.getY() - b.getY()
        );
    }

    /**
     * Выполняет умножение двух векторов.
     * Реализует покомпонентное умножение координат векторов.
     *
     * @param a первый вектор
     * @param b второй вектор
     * @return новый вектор - результат покомпонентного умножения
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Vector multiply(Vector a, Vector b) {
        validateVectors(a, b);

        return new Vector(
                a.getX() * b.getX(),
                a.getY() * b.getY()
        );
    }
    /**
     * Выполняет деление двух векторов.
     * Реализует покомпонентное деление координат векторов (a / b).
     *
     * @param a первый вектор (делимое)
     * @param b второй вектор (делитель)
     * @return новый вектор - результат покомпонентного деления
     * @throws NullPointerException если любой из аргументов равен null
     * @throws ArithmeticException если любая координата вектора b равна нулю
     */
    @Override
    public Vector divide(Vector a, Vector b) {
        validateVectors(a, b);

        if (b.getX() == 0 || b.getY() == 0) {
            throw new ArithmeticException("Деление на ноль: координаты делителя не могут быть нулевыми");
        }

        return new Vector(
                a.getX() / b.getX(),
                a.getY() / b.getY()
        );
    }


    /**
     * Вспомогательный метод для проверки векторов на null.
     *
     * @param a первый вектор
     * @param b второй вектор
     * @throws NullPointerException если любой из аргументов равен null
     */
    private void validateVectors(Vector a, Vector b) {
        if (a == null || b == null) {
            throw new NullPointerException("Векторы не могут быть null");
        }
    }
}