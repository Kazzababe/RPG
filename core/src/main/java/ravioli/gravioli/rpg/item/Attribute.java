package ravioli.gravioli.rpg.item;

public class Attribute {
    private AttributeType type;
    private int amount;

    public Attribute(AttributeType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public AttributeType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
