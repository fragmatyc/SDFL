package com.sdfl.code.filter;

import java.util.function.Function;

/**
 * This interface is a specialisation of the generic java.util.function.Function<T, R>.
 *
 * Implementations are to implement the apply method in such a way that they remove parts of the input String and return
 * the remaining part as a String.
 *
 * Created by IntelliJ IDEA. User: cpt Date: 6-2-16 Time: 13:06
 */
public interface CodeFilter
        extends Function<String, String> {
}
