package impl;

import interfaces.ArithmeticOperations;

/**
 * Реализация арифметических операций для чисел с плавающей точкой (Double).
 *
 * <p>Класс обеспечивает выполнение базовых арифметических операций
 * с числами двойной точности, включая обработку специальных значений
 * (бесконечность, NaN) и проверку на деление на ноль.</p>
 *
 * @version 1.0
 * @see ArithmeticOperations
 * @see Double
 */
public class DoubleArithmetic implements ArithmeticOperations<Double> {

    /**
     * Допустимая погрешность для сравнения с нулём.
     * Используется для проверки деления на ноль.
     */
    private static final double EPSILON = 1e-10;

    /**
     * Складывает два числа с плавающей точкой.
     *
     * @param a первый операнд (не может быть null)
     * @param b второй операнд (не может быть null)
     * @return результат сложения a + b
     * @throws ArithmeticException если результат операции равен бесконечности или NaN
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Double add(Double a, Double b) {
        checkForNull(a, b);
        double result = a + b;
        checkForInfiniteOrNaN(result, "сложении", a, b);
        return result;
    }

    /**
     * Вычитает одно число с плавающей точкой из другого.
     *
     * @param a первый операнд (уменьшаемое, не может быть null)
     * @param b второй операнд (вычитаемое, не может быть null)
     * @return результат вычитания a - b
     * @throws ArithmeticException если результат операции равен бесконечности или NaN
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Double subtract(Double a, Double b) {
        checkForNull(a, b);
        double result = a - b;
        checkForInfiniteOrNaN(result, "вычитании", a, b);
        return result;
    }

    /**
     * Умножает два числа с плавающей точкой.
     *
     * @param a первый операнд (не может быть null)
     * @param b второй операнд (не может быть null)
     * @return результат умножения a * b
     * @throws ArithmeticException если результат операции равен бесконечности или NaN
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Double multiply(Double a, Double b) {
        checkForNull(a, b);
        double result = a * b;
        checkForInfiniteOrNaN(result, "умножении", a, b);
        return result;
    }

    /**
     * Делит одно число с плавающей точкой на другое.
     *
     * <p>Выполняет деление с проверкой на деление на ноль.</p>
     *
     * @param a первый операнд (делимое, не может быть null)
     * @param b второй операнд (делитель, не может быть null)
     * @return результат деления a / b
     * @throws ArithmeticException если делитель близок к нулю (|b| &lt; EPSILON)
     *         или результат операции равен бесконечности или NaN
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Double divide(Double a, Double b) {
        checkForNull(a, b);

        // Проверка деления на ноль (с учётом погрешности)
        if (Math.abs(b) < EPSILON) {
            throw new ArithmeticException("Деление на ноль: " + a + " / " + b);
        }

        double result = a / b;
        checkForInfiniteOrNaN(result, "делении", a, b);
        return result;
    }

    /**
     * Проверяет аргументы на null.
     *
     * @param a первый аргумент для проверки
     * @param b второй аргумент для проверки
     * @throws NullPointerException если любой из аргументов равен null
     */
    private void checkForNull(Double a, Double b) {
        if (a == null || b == null) {
            throw new NullPointerException("Аргументы не могут быть null: a=" + a + ", b=" + b);
        }
    }

    /**
     * Проверяет результат операции на бесконечность или NaN.
     *
     * @param result результат операции для проверки
     * @param operation название операции (для сообщения об ошибке)
     * @param a первый операнд (для сообщения об ошибке)
     * @param b второй операнд (для сообщения об ошибке)
     * @throws ArithmeticException если результат равен бесконечности или NaN
     */
    private void checkForInfiniteOrNaN(double result, String operation, Double a, Double b) {
        if (Double.isInfinite(result)) {
            throw new ArithmeticException("Бесконечность при " + operation + ": " + a + " и " + b);
        }
        if (Double.isNaN(result)) {
            throw new ArithmeticException("NaN при " + operation + ": " + a + " и " + b);
        }
    }
}