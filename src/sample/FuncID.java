package sample;

public class FuncID {
    private int ID;
    private String name;

    public FuncID(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
