package com.github.chrisbrenton.grappa.formal.nodes;

/**
 * A rule definition in a formal grammar
 *
 * <p>A rule consists of two elements:</p>
 *
 * <ul>
 *     <li>its name (a non terminal), on the left hand side;</li>
 *     <li>a production rule on the right hand side.</li>
 * </ul>
 *
 * <p>This interface gives access to those two elements individually.</p>
 *
 * @see ProductionRule
 */
public interface FormalRule
{
    /**
     * Return the name (the left hand side) of this rule definition
     *
     * @return the name (never empty)
     */
    String getName();

    /**
     * Return the production rule (the right hand side) of this rule definition
     *
     * @return the production rule
     */
    ProductionRule getProductionRule();
}
