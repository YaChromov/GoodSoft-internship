import impl.DoubleArithmetic;
import impl.IntegerArithmetic;
import impl.StringArithmetic;
import impl.VectorArithmetic;
import interfaces.ArithmeticOperations;
import entity.Vector;


void main() {

    IO.println("_________________ ЦЕЛЫЕ ЧИСЛА _________________");
    ArithmeticOperations<Integer> integerArithmetic = new IntegerArithmetic();
    IO.println("Сумма целых чисел 6 и 2: " + integerArithmetic.add(6, 2));
    IO.println("Разность целых чисел 6 и 2: " + integerArithmetic.subtract(6, 2));
    IO.println("Произведение целых чисел 6 и 2: " + integerArithmetic.multiply(6, 2));
    IO.println("Частное целых чисел 6 и 2: " + integerArithmetic.divide(6, 2));


    IO.println("________________ ДРОБНЫЕ ЧИСЛА ________________");
    ArithmeticOperations<Double> doubleArithmetic = new DoubleArithmetic();
    IO.println("Сумма чисел 6.5 и 2.3: " + doubleArithmetic.add(6.5, 2.3));
    IO.println("Разность чисел 6.5 и 2.3: " + doubleArithmetic.subtract(6.5, 2.3));
    IO.println("Произведение чисел 6.5 и 2.3: " + doubleArithmetic.multiply(6.5, 2.3));
    IO.println("Частное чисел 6.5 и 2.3: " + doubleArithmetic.divide(6.5, 2.3));


    IO.println("_________________ СТРОКИ ______________________");
    ArithmeticOperations<String> stringArithmetic = new StringArithmetic();
    String str1 = "Hello";
    String str2 = "World";
    IO.println("str1 = \"" + str1 + "\", str2 = \"" + str2 + "\"");
    IO.println("Сложение (конкатенация): \"" + stringArithmetic.add(str1, str2) + "\"");
    IO.println("Вычитание (удаление подстроки): \"" + stringArithmetic.subtract("HelloWorld", "World") + "\"");
    IO.println("Умножение (поэлементное слияние): \"" + stringArithmetic.multiply(str1, str2) + "\"");
    IO.println("Деление (обрезание до длины второго): \"" + stringArithmetic.divide("HelloWorld", "Hi") + "\"");

    IO.println("_________________ ВЕКТОРЫ _____________________");
    ArithmeticOperations<Vector> vectorArithmetic = new VectorArithmetic();

    Vector v1 = new Vector(3, 4);
    Vector v2 = new Vector(1, 2);
    IO.println("v1 = " + v1 + ", v2 = " + v2);
    IO.println("Сложение v1 + v2: " + vectorArithmetic.add(v1, v2).toString());
    IO.println("Вычитание v1 - v2: " + vectorArithmetic.subtract(v1, v2).toString());
    IO.println("Умножение v1 * v2: " + vectorArithmetic.multiply(v1, v2).toString());
    try {
        IO.println("Деление v1 / v2: " + vectorArithmetic.divide(v1, v2).toString());
    } catch (ArithmeticException e) {
        IO.println("Деление v1 / v2: ошибка - " + e.getMessage());
    }


}

