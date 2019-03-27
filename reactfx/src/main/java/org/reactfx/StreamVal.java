package org.reactfx;

import org.reactfx.value.ValBase;

/**
 * @since RFXX
 */
class StreamVal<T> extends ValBase<T> {

    private final EventStream<T> input;
    private T value;

    public StreamVal(EventStream<T> input, T initialValue) {
        this.input = input;
        value = initialValue;
    }

    @Override
    protected Subscription connect() {
        return input.subscribe(evt -> {
            value = evt;
            invalidate();
        });
    }

    @Override
    protected T computeValue() {
        return value;
    }
}
