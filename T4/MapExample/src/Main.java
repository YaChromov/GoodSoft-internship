import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("ключ1", "значение1");
        stringMap.put("ключ2", "значение2");
        stringMap.put("ключ3", "значение3");


        IO.println("Исходная Map (строковые ключи):");
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            IO.println(entry.getKey() + " -> " + entry.getValue());
        }


        stringMap.put("ключ2", "НОВОЕ_значение2");

        IO.println("\nПосле замены значения для ключ2:");
        stringMap.forEach((key, value) -> System.out.println(key + " -> " + value));

        Map<Person, String> personMap = new HashMap<>();
        Person alice = new Person("Алиса", 30);
        Person bob = new Person("Маша", 25);
        personMap.put(alice, "Инженер");
        personMap.put(bob, "Дизайнер");

        IO.println("\nMap с ключами-объектами Person ( до замены ):");
        for (Map.Entry<Person, String> entry : personMap.entrySet()) {
            IO.println(entry.getKey() + " -> " + entry.getValue());
        }

        Person anotherAlice = new Person("Алиса", 30);
        personMap.put(anotherAlice, "Менеджер");

        IO.println("\nПосле добавления ещё одного Person с теми же полями (Алиса,30):");
        personMap.forEach((person, occupation) -> IO.println(person + " -> " + occupation));
    }
}