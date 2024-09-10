package org.fermented.dairy.monads;

public class Result<R, E extends Throwable> {

    private final R result;
    private final E error;

    private Result(final R result, final E error) {
        this.result = result;
        this.error = error;
    }
}
