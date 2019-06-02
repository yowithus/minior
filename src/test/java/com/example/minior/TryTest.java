package com.example.minior;

import org.junit.Test;

import java.math.BigInteger;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class TryTest {

    @Test
    public void whenTryOfSuccess_thenReturnCorrectResult() {
        final BigInteger a = BigInteger.valueOf(12);
        final BigInteger b = BigInteger.valueOf(5);
        final BigInteger sum = BigInteger.valueOf(17);

        assertEquals(Try.of(() -> a.add(b)).get(), sum);
    }

    @Test
    public void whenTryOfFailed_thenReturnDefaultValue() {
        final BigInteger a = BigInteger.valueOf(1);
        final BigInteger b = BigInteger.valueOf(0);
        final BigInteger defaultVal = BigInteger.valueOf(10);

        assertEquals(Try.of(() -> a.divide(b)).onFailed(err -> System.out.println(err)).orElse(defaultVal), defaultVal);
    }

    @Test
    public void whenTryOfFilterMatched_thenReturnCorrectResult() {
        final BigInteger a = BigInteger.valueOf(6);
        final BigInteger five = BigInteger.valueOf(5);
        final Predicate<BigInteger> isGreaterThanFive = i -> i.compareTo(five) > 0;
        final BigInteger defaultValue = BigInteger.ZERO;

        assertEquals(Try.of(() -> a).filter(isGreaterThanFive).orElse(defaultValue), a);
    }

    @Test
    public void whenTryOfFilterNotMatched_thenReturnDefaultValue() {
        final BigInteger a = BigInteger.valueOf(1);
        final BigInteger five = BigInteger.valueOf(5);
        final Predicate<BigInteger> isGreaterThanFive = i -> i.compareTo(five) > 0;
        final BigInteger defaultValue = BigInteger.ZERO;

        assertEquals(Try.of(() -> a).filter(isGreaterThanFive).orElse(defaultValue), defaultValue);
    }
}
