package io;

import exceptions.InvalidDataException;

/**
 * user input callback
 * @param <T>
 */
@FunctionalInterface
public interface Askable<T> {
    T ask() throws InvalidDataException;
}
