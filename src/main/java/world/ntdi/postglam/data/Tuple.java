package world.ntdi.postglam.data;

import lombok.Getter;

/**
 * A Tuple
 * @param <A> Type Value A
 * @param <B> Type Value B
 */
public class Tuple<A, B> {

    @Getter
    private final A a;

    @Getter
    private final B b;

    /**
     * Create a new Tuple.
     * This can be done like so {@code new Tuple<String, Boolean>("Hello World", true);}
     * Then you can access either a or b, a being the First value or the Left and b being the Second value or the Right.
     * @param a
     * @param b
     */
    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }
}
