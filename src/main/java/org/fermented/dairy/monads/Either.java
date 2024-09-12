package org.fermented.dairy.monads;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Either Monad.
 *
 * @param <L> the left type.
 * @param <R> the right type.
 */
public class Either<L, R> {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<L> left;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<R> right;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
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

    /**
     * Get the left value.
     * <p>
     * Throws {@link NoSuchElementException} if there is no left value present.
     *
     * @return The left value
     */
    public L left() {
        return left.orElseThrow(() -> new NoSuchElementException("Left value not present"));
    }

    /**
     * Get the right value.
     * <p>
     * Throws {@link NoSuchElementException} if there is no right value present.
     *
     * @return The left value
     */
    public R right() {
        return right.orElseThrow(() -> new NoSuchElementException("Right value not present"));
    }

    /**
     * @return true if the left value is present (left is only present if right is not present).
     */
    public boolean hasLeft() {
        return left.isPresent();
    }

    /**
     * @return true if the right value is present (right is only present if left is not present).
     */
    public boolean hasRight() {
        return !hasLeft();
    }

    /**
     * Applies the given to the left value (See {@link Optional#map(Function)} and leaves the right value untouched.
     *
     * @param function The mapping function
     *                 
     * @return A new Either with a mapped left value
     * @param <NL> the new type for the left value
     */
    public <NL> Either<NL, R> mapLeft(final Function<L, NL> function) {
        return new Either<>(left.map(function), right);
    }

    /**
     * Applies the given to the right value (See {@link Optional#map(Function)} and leaves the left value untouched.
     *
     * @param function The mapping function
     *
     * @return A new Either with a mapped right value
     * @param <NR> the new type for the right value
     */
    public <NR> Either<L, NR> mapRight(final Function<R, NR> function) {
        return new Either<>(left, right.map(function));
    }

    /**
     * Creates a new Either object with the provided left value and an empty right value.
     *
     * @param left The left value.
     *
     * @return A new Either object with the provided left value and an empty right value.
     * @param <L> The left type
     * @param <R> The right type
     */
    @Contract("_ -> new")
    public static <L, R> @NotNull Either<L, R> ofLeft(final L left) {
        return new Either<>(Optional.ofNullable(left), Optional.empty());
    }

    /**
     * Creates a new Either object with the provided right value and an empty left value.
     *
     * @param right The right value.
     *
     * @return A new Either object with the provided right value and an empty left value.
     * @param <L> The left type
     * @param <R> The right type
     */
    @Contract("_ -> new")
    public static <L, R> @NotNull Either<L, R> ofRight(final R right) {
        return new Either<>(Optional.empty(), Optional.ofNullable(right));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Either<?, ?> either = (Either<?, ?>) o;
        return Objects.equals(left, either.left) && Objects.equals(right, either.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "Either{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
