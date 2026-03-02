//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Comparator<Person> ageComparator = Comparator.comparingInt(Person::getAge);

        Map<String, Integer> stringKeyMap = new TreeMap<>();
        stringKeyMap.put("Иван", 30);
        stringKeyMap.put("Анна", 25);
        stringKeyMap.put("Петр", 35);
        stringKeyMap.put("Мария", 20);
        stringKeyMap.put("Борис", 40);
       IO.println("TreeMap (ключи-строки, отсортированы по алфавиту):");
        for (Map.Entry<String, Integer> entry : stringKeyMap.entrySet()) {
            IO.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        IO.println("===============================================");

        Map<Person, String> personKeyMap = new TreeMap<>(ageComparator);
        personKeyMap.put(new Person("Иван", 30), "Инженер");
        personKeyMap.put(new Person("Анна", 25), "Врач");
        personKeyMap.put(new Person("Петр", 35), "Учитель");
        personKeyMap.put(new Person("Мария", 20), "Студент");
        personKeyMap.put(new Person("Борис", 40), "Директор");
        IO.println("\nTreeMap (ключи-Person, отсортированы по возрасту):");
        for (Map.Entry<Person, String> entry : personKeyMap.entrySet()) {
            IO.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}