package seedu.address.logic.graph;

import java.util.Objects;

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
    public boolean equals(Object other) {
        return other == this
            || other instanceof Vertex && Objects.equals(item, ((Vertex) other).item)
                && Objects.equals(partner, ((Vertex) other).partner);
    }

    @Override
    public String toString() {
        return String.format("U: %s, V: %s", String.valueOf(item), String.valueOf(partner));
    }
}
