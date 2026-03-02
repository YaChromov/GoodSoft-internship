
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> originalList = new ArrayList<>();
        originalList.add("A");
        originalList.add("B");
        originalList.add("C");
        originalList.add("D");

        IO.println("Исходный список: " + originalList);

        IO.println("\nВывод с помощью foreach:");
        for (String item : originalList) {
            IO.print(item + " ");
        }

        IO.println("\nВставка элемента во время обхода обычным for");
        List<String> listForInsert = new ArrayList<>(originalList);
        IO.println("Список до вставки: " + listForInsert);

        for (int i = 0; i < listForInsert.size(); i++) {
            String element = listForInsert.get(i);
            IO.println("Индекс " + i + ", элемент: " + element);
            if (i == 2) {
                IO.println("  -> Вставляем элемент 'X' на позицию 2");
                listForInsert.add(2, "X");
            }
        }
        IO.println("Список после вставки: " + listForInsert);
        IO.println("\nУдаление элемента во время обхода обычным for");
        List<String> listForDelete = new ArrayList<>(originalList);
        IO.println("Список до удаления: " + listForDelete);

        for (int i = 0; i < listForDelete.size(); i++) {
            String element = listForDelete.get(i);
            IO.println("Индекс " + i + ", элемент: " + element);
            if (i == 2) {
                IO.println("  -> Удаляем элемент с позиции 2");
                listForDelete.remove(2);
            }
        }
        IO.println("Список после удаления: " + listForDelete);
    }
}
