package seedu.address.logic;

public class Vertex<U, V> {
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
}
