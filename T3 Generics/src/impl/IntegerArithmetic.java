package impl;


import interfaces.ArithmeticOperations;

/**
 * Реализация арифметических операций для целых чисел (Integer).
 *
 * <p>Класс обеспечивает выполнение базовых арифметических операций
 * с проверкой на переполнение и деление на ноль.</p>
 *
 * @version 1.0
 * @see ArithmeticOperations
 * @see Integer
 */
public class IntegerArithmetic implements ArithmeticOperations<Integer> {

    /**
     * Складывает два целых числа с проверкой на переполнение.
     *
     * @param a первый операнд (не может быть null)
     * @param b второй операнд (не может быть null)
     * @return результат сложения a + b
     * @throws ArithmeticException если происходит переполнение при сложении
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Integer add(Integer a, Integer b) {
        checkForNull(a, b);
        try {
            return Math.addExact(a, b);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Переполнение при сложении: " + a + " + " + b);
        }
    }

    /**
     * Вычитает одно целое число из другого с проверкой на переполнение.
     *
     * @param a первый операнд (уменьшаемое, не может быть null)
     * @param b второй операнд (вычитаемое, не может быть null)
     * @return результат вычитания a - b
     * @throws ArithmeticException если происходит переполнение при вычитании
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Integer subtract(Integer a, Integer b) {
        checkForNull(a, b);
        try {
            return Math.subtractExact(a, b);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Переполнение при вычитании: " + a + " - " + b);
        }
    }

    /**
     * Умножает два целых числа с проверкой на переполнение.
     *
     * @param a первый операнд (не может быть null)
     * @param b второй операнд (не может быть null)
     * @return результат умножения a * b
     * @throws ArithmeticException если происходит переполнение при умножении
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Integer multiply(Integer a, Integer b) {
        checkForNull(a, b);
        try {
            return Math.multiplyExact(a, b);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Переполнение при умножении: " + a + " * " + b);
        }
    }

    /**
     * Делит одно целое число на другое.
     *
     * <p>Выполняет целочисленное деление с проверкой на деление на ноль
     * и специальный случай Integer.MIN_VALUE / -1.</p>
     *
     * @param a первый операнд (делимое, не может быть null)
     * @param b второй операнд (делитель, не может быть null)
     * @return результат деления a / b (целочисленное деление)
     * @throws ArithmeticException если делитель равен нулю или происходит
     *         переполнение при делении Integer.MIN_VALUE на -1
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public Integer divide(Integer a, Integer b) {
        checkForNull(a, b);

        if (b == 0) {
            throw new ArithmeticException("Деление на ноль: " + a + " / " + b);
        }

        if (a == Integer.MIN_VALUE && b == -1) {
            throw new ArithmeticException("Переполнение при делении: " + a + " / " + b);
        }

        return a / b;
    }

    /**
     * Проверяет аргументы на null.
     *
     * @param a первый аргумент для проверки
     * @param b второй аргумент для проверки
     * @throws NullPointerException если любой из аргументов равен null
     */
    private void checkForNull(Integer a, Integer b) {
        if (a == null || b == null) {
            throw new NullPointerException("Аргументы не могут быть null: a=" + a + ", b=" + b);
        }
    }
}