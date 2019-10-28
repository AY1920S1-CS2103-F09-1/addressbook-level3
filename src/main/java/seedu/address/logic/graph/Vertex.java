package seedu.address.logic.graph;

public abstract class Vertex<U, V> {
    private U item;
    private V partner;

    public Vertex(U item) {
        this.item = item;
    }

    public void match(V partner) {
        this.partner = partner;
    }

    public void unmatch() {
        this.partner = null;
    }

    public boolean isMatched() {
        return this.partner != null;
    }

    @Override
    public String toString() {
        return String.format("U: %s, V: %s", item.toString(), partner.toString());
    }
}
