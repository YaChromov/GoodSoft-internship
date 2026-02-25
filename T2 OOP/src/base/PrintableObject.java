package base;

public abstract class PrintableObject {
    protected String name;
    public abstract String getInfo();

    public void printInfo() {
        System.out.println(getInfo());
    }
}