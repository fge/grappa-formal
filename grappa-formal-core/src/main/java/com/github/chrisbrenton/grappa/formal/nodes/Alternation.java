package com.github.chrisbrenton.grappa.formal.nodes;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;

/**
 * An alternation in a production rule
 *
 * <p>In the following <a href="https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_Form"
 * target="_blank">EBNF</a> example:</p>
 *
 * <pre>
 *     number = [ "-" ], decimal | octal | hexadecimal ;
 * </pre>
 *
 * <p>the different alternations for the {@code number} rule are:</p>
 *
 * <ul>
 *     <li>{@code [ "-" ], decimal},</li>
 *     <li>{@code octal},</li>
 *     <li>{@code hexadecimal}.</li>
 * </ul>
 *
 * <p>An alternation is further divided into its individual elements.
 * Implementations of this interface must unwrap any repetition operators that
 * the formalism may define (such as {@code [ ... ]} in the above example). For
 * example, the elements of the first alternation in the list above are:</p>
 *
 * <ul>
 *     <li>{@code "-"},</li>
 *     <li>{@code decimal}.</li>
 * </ul>
 *
 * <p>An alternation always has at least one element. Duplicates <em>should
 * not</em> be pruned.</p>
 *
 * @see ProductionRule#getAlternations()
 * @see AlternationElement
 */
@FunctionalInterface
public interface Alternation
{
    /**
     * Get the list of the individual elements of this alternation
     *
     * <p>Note that the elements must appear in their order of appearance in the
     * returned list. The returned list must be immutable (use {@link
     * ImmutableList}, or {@link Collections#unmodifiableList(List)}).</p>
     *
     * @return see description
     *
     * @see AlternationElement
     */
    List<AlternationElement> getElements();
}
