package org.reactfx;


class GuardedStream<T> extends EventStreamBase<T> {
    private final EventStream<T> source;
    private final Guardian guardian;

    public GuardedStream(EventStream<T> source, Guardian... guardians) {
        this.source = source;
        this.guardian = Guardian.combine(guardians);
    }

    @Override
    protected Subscription observeInputs() {
        return source.subscribe(evt -> {
            try(Guard g = guardian.guard()) {
                emit(evt);
            }
        });
    }
}