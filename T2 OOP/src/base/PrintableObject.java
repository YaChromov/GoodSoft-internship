package base;

public abstract class PrintableObject {
    public abstract String getInfo();

    public void printInfo() {
        System.out.println(getInfo());
    }


}