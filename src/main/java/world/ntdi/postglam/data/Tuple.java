package world.ntdi.postglam.data;

import lombok.Getter;

public class Tuple<A, B> {

    @Getter
    private final A a;

    @Getter
    private final B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }
}
