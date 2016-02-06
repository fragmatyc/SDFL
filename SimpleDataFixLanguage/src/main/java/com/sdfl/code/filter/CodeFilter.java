package com.sdfl.code.filter;

/**
 * Created by IntelliJ IDEA. User: cpt Date: 6-2-16 Time: 13:06
 */
@FunctionalInterface
public interface CodeFilter<T> {
/**
 * Filters any class given as input, returning a cleansed version of the same class having removed any unwanted
 * contents
 * @param input An object containing the uncleansed contents
 * @return a new object with the unwanted contents removed
 */
T filter(T input);
}
