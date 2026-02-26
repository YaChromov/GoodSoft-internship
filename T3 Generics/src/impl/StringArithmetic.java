package impl;

import interfaces.ArithmeticOperations;

/**
 * Реализация арифметических операций над строками.
 *
 * <p>Класс обеспечивает выполнение 'арифметических' операций над строкой </p>
 *
 * @version 1.0
 * @see ArithmeticOperations
 * @see String
 */
public class StringArithmetic implements ArithmeticOperations<String> {

    /**
     * Выполняет операцию сложения (конкатенации) двух строк.
     * Метод объединяет первую и вторую строки в одну новую строку.
     *
     * @param str1 первая строка для сложения (не может быть null)
     * @param str2 вторая строка для сложения (не может быть null)
     * @return новая строка, представляющая собой результат конкатенации str1 и str2
     * @throws NullPointerException если любой из аргументов равен null
     *
     * @see String#concat(String)
     */
    @Override
    public String add(String str1, String str2) {
        return str1.concat(str2);
    }

    /**
     * Выполняет операцию вычитания одной строки из другой.
     * Удаляет все вхождения подстроки str2 из строки str1.
     *
     * @param str1 исходная строка, из которой производится вычитание
     * @param str2 подстрока для удаления из str1
     * @return новая строка, полученная путем удаления всех вхождений str2 из str1
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public String subtract(String str1, String str2) {
        return str1.replace(str2, "");
    }

    /**
     * Выполняет операцию умножения (поэлементного слияния) двух строк.
     *
     * @param str1 первая строка для умножения
     * @param str2 вторая строка для умножения
     * @return новая строка, полученная путем поочередного слияния символов из str1 и str2
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public String multiply(String str1, String str2) {
        StringBuilder result = new StringBuilder();
        int maxLength = Math.max(str1.length(), str2.length());

        for (int i = 0; i < maxLength; i++) {
            if (i < str1.length()) {
                result.append(str1.charAt(i));
            }
            if (i < str2.length()) {
                result.append(str2.charAt(i));
            }
        }
        return result.toString();
    }

    /**
     * Выполняет операцию деления строки (обрезание первой строки до длины второй строки).
     * Возвращает подстроку из str1, длиной равной длине str2.
     * Если str1 короче или равна по длине str2, возвращается исходная строка str1.
     *
     * @param str1 исходная строка, которую необходимо обрезать
     * @param str2 строка, длина которой определяет конечную длину результата
     * @return подстроку str1 длиной до длины str2, или str1, если ее длина не превышает длину str2
     * @throws NullPointerException если любой из аргументов равен null
     */
    @Override
    public String divide(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return str1;
        }

        int targetLength = str2.length();

        if (str1.length() <= targetLength) {
            return str1;
        }

        return str1.substring(0, targetLength);
    }
}