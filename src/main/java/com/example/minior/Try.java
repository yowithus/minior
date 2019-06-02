package com.example.minior;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Try<T> {

    private final T result;
    private final Throwable cause;

    private Try(T result, Throwable cause) {
        this.result = result;
        this.cause = cause;
    }

    public static <U> Try<U> of(Supplier<U> supplier) {
        try {
            return new Try<>(supplier.get(), null);
        } catch (Throwable cause) {
            return new Try<>(null, cause);
        }
    }

    public T get() {
        return this.result;
    }

    public T orElse(T otherValue) {
        return this.isFailed() ? otherValue : this.result;
    }

    public T orElseGet(Supplier<? extends T> other) {
        return this.isFailed() ? other.get() : this.result;
    }

    public Try<T> onFailed(Consumer<? super Throwable> consumer) {
        if (this.isFailed()) {
            consumer.accept(this.cause);
        }
        return this;
    }

    public Try<T> filter(Predicate<T> predicate) {
        if (this.isFailed()) {
            return this;
        }

        return predicate.test(this.result) ? this : new Try(null, new Throwable("predicate does not hold"));
    }

    private boolean isFailed() {
        return this.cause != null;
    }

    private boolean isSuccess() {
        return this.cause == null;
    }
}
