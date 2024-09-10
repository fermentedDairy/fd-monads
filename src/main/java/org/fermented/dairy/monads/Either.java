package org.fermented.dairy.monads;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class Either<L, R> {

    private final Optional<L> left;
    private final Optional<R> right;

    private Either(final Optional<L> left, final Optional<R> right) {
        this.left = Objects.requireNonNullElse(left, Optional.empty());
        this.right = Objects.requireNonNullElse(right, Optional.empty());
        if (left.isPresent() && right.isPresent()) {
            throw new IllegalArgumentException("Both left and right are both provided");
        }
        if (left.isEmpty() && right.isEmpty()) {
            throw new IllegalArgumentException("Both left and right are both not provided");
        }
    }

    public L left() {
        return left.orElseThrow(() -> new NoSuchElementException("Left value not present"));
    }

    public R right() {
        return right.orElseThrow(() -> new NoSuchElementException("Right value not present"));
    }

    public boolean hasLeft() {
        return left.isPresent();
    }

    public boolean hasRight() {
        return !hasLeft();
    }

    public <NL> Either<NL, R> mapLeft(final Function<L, NL> function) {
        return new Either<>(left.map(function), right);
    }

    public <NR> Either<L, NR> mapRight(final Function<R, NR> function) {
        return new Either<>(left, right.map(function));
    }

    @Contract("_ -> new")
    public static <L, R> @NotNull Either<L, R> ofLeft(final L left) {
        return new Either<>(Optional.ofNullable(left), Optional.empty());
    }


    @Contract("_ -> new")
    public static <L, R> @NotNull Either<L, R> ofRight(final R right) {
        return new Either<>(Optional.empty(), Optional.ofNullable(right));
    }
}
