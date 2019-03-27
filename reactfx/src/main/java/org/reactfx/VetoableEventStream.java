package org.reactfx;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import org.reactfx.util.Timer;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableBooleanValue;

/**
 * An event stream whose events can be vetoed during a certain period, after which they are
 * emitted.
 *
 * @since RFXX
 * @author Clément Fournier
 */
final class VetoableEventStream<I> extends EventStreamBase<I> implements AwaitingEventStream<I> {

    private final EventStream<I> input;
    private final BiFunction<I, I, I> vetoableReduction;
    private final Predicate<I> isVetoable;
    private final BiPredicate<I, I> isVetoSignal;

    private final Timer timer;

    private BooleanBinding pending = null;
    private I vetoable = null;

    VetoableEventStream(
        EventStream<I> input,
        BiFunction<I, I, I> vetoableReduction,
        Predicate<I> isVetoable,
        BiPredicate<I, I> isVeto,
        Function<Runnable, Timer> timerFactory) {

        this.input = input;
        this.vetoableReduction = vetoableReduction;
        this.isVetoable = isVetoable;
        this.isVetoSignal = isVeto;
        this.timer = timerFactory.apply(this::handleTimeout);
    }

    @Override
    public ObservableBooleanValue pendingProperty() {
        if (pending == null) {
            pending = new BooleanBinding() {
                @Override
                protected boolean computeValue() {
                    return vetoable != null;
                }
            };
        }
        return pending;
    }

    @Override
    public boolean isPending() {
        return pending != null ? pending.get() : vetoable != null;
    }

    @Override
    protected Subscription observeInputs() {
        return input.subscribe(this::handleEvent);
    }

    private void handleEvent(I i) {
        if (vetoable != null) {
            if (isVetoSignal.test(vetoable, i)) {
                timer.stop();
                vetoable = null;
                invalidatePending();
                emit(i);
            } else if (isVetoable.test(i)) {
                I reduced = vetoableReduction.apply(vetoable, i);
                vetoable = null;
                handleEvent(reduced); // test whether the reduced is vetoable
            }
        } else {
            if (isVetoable.test(i)) {
                vetoable = i;
                timer.restart();
                invalidatePending();
            } else {
                emit(i);
            }
        }
    }

    private void handleTimeout() {
        if (vetoable == null) {
            return;
        }
        I toEmit = vetoable;
        vetoable = null;
        emit(toEmit);
        invalidatePending();
    }

    private void invalidatePending() {
        if (pending != null) {
            pending.invalidate();
        }
    }


}
