
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Set<String> stringSet = new TreeSet<>();
        stringSet.add("банан");
        stringSet.add("яблоко");
        stringSet.add("апельсин");
        stringSet.add("груша");
        stringSet.add("вишня");

        IO.println("Отсортированное множество строк:");
        for (String s : stringSet) {
            IO.println(s);
        }

        IO.println("\n===============================================");

        Set<Person> personSet = new TreeSet<>();
        personSet.add(new Person("Анна", 22));
        personSet.add(new Person("Иван", 30));
        personSet.add(new Person("Мария", 22));
        personSet.add(new Person("Пётр", 23));
        personSet.add(new Person("Елена", 22));

       IO.println("\nОтсортированное множество Person (по возрасту):");
        for (Person p : personSet) {
            IO.println(p);
        }
    }
}