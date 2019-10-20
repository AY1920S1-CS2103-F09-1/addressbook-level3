package seedu.address.commons.util;

public class Pair<H, T> {
    private H head;
    private T tail;

    public Pair(H head, T tail) {
        this.head = head;
        this.tail = tail;
    }

    public H getHead() {
        return head;
    }

    public T getTail() {
        return tail;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", head, tail);
    }
}
