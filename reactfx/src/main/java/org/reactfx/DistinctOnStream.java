package org.reactfx;

import java.util.Objects;
import java.util.function.Function;

import org.reactfx.util.Timer;

/**
 * @see org.reactfx.EventStream#distinctOn()
 */
class DistinctOnStream<I> extends EventStreamBase<I> {

    static final Object NONE = new Object();
    private final EventStream<I> input;
    private final Timer timer;
    private Object previous = NONE;

    DistinctOnStream(EventStream<I> input, Function<Runnable, Timer> timerFactory) {
        this.input = input;
        this.timer = timerFactory.apply(() -> previous = NONE);
    }

    @Override
    protected Subscription observeInputs() {
        return input.subscribe(value -> {
            Object prevToCompare = previous;
            previous = value;
            timer.restart();
            if (!Objects.equals(value, prevToCompare)) {
                emit(value);
            }
        });
    }

}
