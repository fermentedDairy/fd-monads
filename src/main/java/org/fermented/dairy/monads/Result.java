package org.fermented.dairy.monads;

import java.util.Optional;

public class Result<R, E extends Throwable> {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<R> result;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<E> error;

    private Result(final R result, final E error) {
        this.result = Optional.of(result);
        this.error = Optional.of(error);
    }
}
