package org.reactfx.util;

import static javafx.animation.Interpolator.EASE_BOTH;
import static javafx.animation.Interpolator.EASE_IN;
import static javafx.animation.Interpolator.EASE_OUT;
import static javafx.animation.Interpolator.LINEAR;

import javafx.animation.Interpolatable;

/**
 * Interpolates values between two boundary values.
 *
 * <p>This is a simpler and more flexible interface than the class
 * {@link javafx.animation.Interpolator}. Simpler, because it only interpolates
 * values of one type, {@code T}. More flexible, because the values to
 * interpolate don't have to be numbers nor implement
 * {@linkplain javafx.animation.Interpolatable}.
 *
 * @param <T> type of the values to interpolate
 */
@FunctionalInterface
public interface Interpolator<T> {

    T interpolate(T start, T end, double fraction);


    Interpolator<Double> LINEAR_DOUBLE =
            (a, b, frac) -> LINEAR.interpolate(a.doubleValue(), b.doubleValue(), frac);

    Interpolator<Integer> LINEAR_INTEGER =
            (a, b, frac) -> LINEAR.interpolate(a.intValue(), b.intValue(), frac);

    Interpolator<Long> LINEAR_LONG =
            (a, b, frac) -> LINEAR.interpolate(a.longValue(), b.longValue(), frac);

    Interpolator<Number> LINEAR_NUMBER =
            (a, b, frac) -> (Number) LINEAR.interpolate(a, b, frac);


    Interpolator<Double> EASE_BOTH_DOUBLE =
            (a, b, frac) -> EASE_BOTH.interpolate(a.doubleValue(), b.doubleValue(), frac);

    Interpolator<Integer> EASE_BOTH_INTEGER =
            (a, b, frac) -> EASE_BOTH.interpolate(a.intValue(), b.intValue(), frac);

    Interpolator<Long> EASE_BOTH_LONG =
            (a, b, frac) -> EASE_BOTH.interpolate(a.longValue(), b.longValue(), frac);

    Interpolator<Number> EASE_BOTH_NUMBER =
            (a, b, frac) -> (Number) EASE_BOTH.interpolate(a, b, frac);


    Interpolator<Double> EASE_IN_DOUBLE =
            (a, b, frac) -> EASE_IN.interpolate(a.doubleValue(), b.doubleValue(), frac);

    Interpolator<Integer> EASE_IN_INTEGER =
            (a, b, frac) -> EASE_IN.interpolate(a.intValue(), b.intValue(), frac);

    Interpolator<Long> EASE_IN_LONG =
            (a, b, frac) -> EASE_IN.interpolate(a.longValue(), b.longValue(), frac);

    Interpolator<Number> EASE_IN_NUMBER =
            (a, b, frac) -> (Number) EASE_IN.interpolate(a, b, frac);


    Interpolator<Double> EASE_OUT_DOUBLE =
            (a, b, frac) -> EASE_OUT.interpolate(a.doubleValue(), b.doubleValue(), frac);

    Interpolator<Integer> EASE_OUT_INTEGER =
            (a, b, frac) -> EASE_OUT.interpolate(a.intValue(), b.intValue(), frac);

    Interpolator<Long> EASE_OUT_LONG =
            (a, b, frac) -> EASE_OUT.interpolate(a.longValue(), b.longValue(), frac);

    Interpolator<Number> EASE_OUT_NUMBER =
            (a, b, frac) -> (Number) EASE_OUT.interpolate(a, b, frac);


    static <T extends Interpolatable<T>> Interpolator<T> get() {
        return (a, b, frac) -> a.interpolate(b, frac);
    }
}
