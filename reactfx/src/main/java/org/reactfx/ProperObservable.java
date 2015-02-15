package org.reactfx;

/**
 * An {@linkplain Observable} that maintains a collection of registered
 * observers and notifies them when a change occurs. This is unlike
 * {@link ProxyObservable}, which registers observers with an underlying
 * {@linkplain Observable}, and unlike {@link RigidObservable}, which does
 * produce any notifications.
 * @param <O> observer type accepted by this {@linkplain Observable}
 * @param <T> notification type produced by this {@linkplain Observable}
 */
public interface ProperObservable<O, T> extends Observable<O> {
    void notifyObservers(T event);

    default int defaultHashCode() { return System.identityHashCode(this); }
    default boolean defaultEquals(Object o) { return this == o; }
    default String defaultToString() {
        return getClass().getName() + '@' + Integer.toHexString(hashCode());
    }
}