package com.github.chrisbrenton.grappa.formal.nodes;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;

/**
 * A production rule (ie, the right hand side of a rule definition in a formal
 * grammar)
 *
 * <p>A production rule can consist of anything from a single literal up to two
 * alternations or more. Even a single literal, however, will be embedded in an
 * alternation with regards to this interface.</p>
 *
 * @see FormalRule#getProductionRule()
 * @see Alternation
 */
@FunctionalInterface
public interface ProductionRule
{
    /**
     * Get the list of alternations in this production rule
     *
     * <p>Note that the alternations must appear in their order of appearance.
     * The returned list must be immutable (use {@link ImmutableList} or
     * {@link Collections#unmodifiableList(List)}).</p>
     *
     * @return a list of alternations; may have only one element if the
     * production rule only has a single element.
     */
    List<Alternation> getAlternations();
}
