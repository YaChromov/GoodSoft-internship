import java.util.*;


public class Main {
    public static void main(String[] args) {
        Set<String> stringSet = new HashSet<>();
        stringSet.add("яблоко");
        stringSet.add("банан");
        stringSet.add("апельсин");
        stringSet.add("груша");
        stringSet.add("киви");

        IO.println("Множество строк после добавления 5 элементов:");
        for (String s : stringSet) {
            IO.println(s);
        }

        boolean addedString = stringSet.add("яблоко");
        IO.println("\nПопытка добавить 'яблоко' повторно. Результат: " + addedString);

        IO.println("\nМножество после попытки добавления дубликата (итератор):");
        Iterator<String> iterator = stringSet.iterator();
        while (iterator.hasNext()) {
            IO.println(iterator.next());
        }
        IO.println("Размер множества строк: " + stringSet.size());

        IO.println("=========================================================================");

        Set<Product> productSet = new HashSet<>();
        productSet.add(new Product(1, "Ноутбук"));
        productSet.add(new Product(2, "Мышь"));
        productSet.add(new Product(3, "Клавиатура"));
        productSet.add(new Product(4, "Монитор"));
        productSet.add(new Product(5, "Колонки"));

        IO.println("\nМножество товаров после добавления 5 различных:");
        for (Product p : productSet) {
            IO.println(p);
        }

        Product duplicateProduct = new Product(3, "Клавиатура");
        boolean addedProduct = productSet.add(duplicateProduct);
        IO.println("\nПопытка добавить дубликат товара (id=3, name='Клавиатура'). Результат: " + addedProduct);
        IO.println("");
        IO.println("\nМножество товаров после попытки добавления дубликата:");
        for (Product p : productSet) {
            IO.println(p);
        }
        IO.println("Размер множества товаров: " + productSet.size());
    }
}