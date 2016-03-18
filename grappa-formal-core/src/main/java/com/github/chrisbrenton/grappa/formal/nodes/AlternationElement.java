package com.github.chrisbrenton.grappa.formal.nodes;

/**
 * An individual, unwrapped element of an alternation: a terminal, or a
 * reference to a non terminal
 *
 * <p>In the following <a
 * href="https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_Form"
 * target="_blank">EBNF</a> example:</p>
 *
 * <pre>
 *     letter , { letter | digit | "_" }
 * </pre>
 *
 * <p>the list of elements is:</p>
 *
 * <pre>
 *     letter, letter, digit, "_"
 * </pre>
 *
 * @see Alternation#getElements()
 */
public interface AlternationElement
{
    /**
     * Tells whether this element is a terminal
     *
     * @return see description
     */
    boolean isTerminal();

    /**
     * Get the value of this element, as a string
     *
     * <p>For terminals, this will return the literal represented by this
     * element. For non terminals, this will return the name of this non
     * terminal.</p>
     *
     * @return see description
     */
    String getValue();
}
