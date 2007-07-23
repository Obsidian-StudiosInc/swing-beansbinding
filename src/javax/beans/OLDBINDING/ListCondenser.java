/*
 * Copyright (C) 2007 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
 */

package javax.beans.binding;

import java.util.List;

/**
 * {@code ListCondenser} is used to condense a list of values into a
 * single value. {@code ListCondenser} is used by {@code Binding} to select
 * the value for the target when evaluating an expression that yields a list
 * as a part of the expression.
 *
 * @author sky
 */
public abstract class ListCondenser {
    /**
     * Creates and returns a {@code ListCondenser} that returns a string
     * representation of each of the elements supplied to the {@code condense}
     * method. The resulting string is generated by concatenating the results of
     * invoking {@code toString} on each of the elements. The string value
     * is wrapped in the {@code leading} and {@code trailing} strings, and if
     * multiple values are present, each is separated by a {@code separator}
     * string. A value of {@code null} in the list is treated as an empty
     * string.
     *
     * @param leading a string to prefix each element with; a value of 
     *        {@code null} is treated as an empty string
     * @param trailing a string to append to each element; a value of 
     *        {@code null} is treated as an empty string
     * @param separator string used to separate each element; a value of 
     *        {@code null} is treated as an empty string
     * @return a condenser that concatenates the elements
     */
    public static ListCondenser concatenatingCondenser(String leading,
            String trailing, String separator) {
        return new UnioningCondenser(leading, trailing, separator);
    }
    
    /**
     * Condenses the list into a single value. Subclasses can expect the
     * list to be {@code non-null}, but elements of the list may be
     * {@code null}.
     *
     * @param elements the {@code List} of elements to condense
     * @return the appropriate element
     *
     * @throws NullPointerException if {@code elements} is {@code null}
     * @throws IndexOutOfBoundsException if {@code elements} is empty
     */
    public abstract Object condense(List<?> elements);
    
    
    /**
     * {@code UnioningSelector} unions the set of elements passed to the
     * {@code condense} method. Each element may be surrounded by a particular
     * string, as well as separated by a string. For example, the following
     * creates a {@code UnioningSelector} that surrounds each element with
     * quotes, and separates multiple elements with commas:
     * <pre>
     *   new UnioningSelector("\"", "\"", ", ");
     * </pre>
     */
    private static final class UnioningCondenser extends ListCondenser {
        private final String leading;
        private final String trailing;
        private final String separator;
        
        /**
         * Creates a {@code UnioningSelector} where each element is surrouned
         * by strings and separated by a comma.
         */
        public UnioningCondenser() {
            this("\"", "\"", ", ");
        }
        
        /**
         * Creates a {@code UnioningSelector} with the specified set of
         * arguments. A value of {@code null} equates to an empty string.
         *
         * @param leading the string to prefix each element with
         * @param trailing the string to follow each element with
         * @param separator the string to separate each of the elements with
         */
        public UnioningCondenser(String leading, String trailing, String separator) {
            if (leading == null) {
                leading = "";
            }
            if (trailing == null) {
                trailing = "";
            }
            if (separator == null) {
                separator = "";
            }
            this.leading = leading;
            this.trailing = trailing;
            this.separator = separator;
        }
        
        /**
         * Condenses the list into a single value. Each element is wrapped
         * by the {@code leading} and {@code trailing} strings specified
         * via the constructor. Each element is separated by the separator.
         *
         * @param elements the {@code List} of elements to condense
         * @return the appropriate element
         *
         * @throws NullPointerException if {@code elements} is {@code null}
         * @throws IndexOutOfBoundsException if {@code elements} is empty
         */
        public Object condense(List<?> elements) {
            StringBuilder builder = new StringBuilder(leading);
            Object e = elements.get(0);
            if (e != null) {
                builder.append(e.toString());
            }
            builder.append(trailing);
            for (int i = 1; i < elements.size(); i++) {
                builder.append(separator);
                builder.append(leading);
                e = elements.get(i);
                if (e != null) {
                    builder.append(e.toString());
                }
                builder.append(trailing);
            }
            return builder.toString();
        }
    }
}
