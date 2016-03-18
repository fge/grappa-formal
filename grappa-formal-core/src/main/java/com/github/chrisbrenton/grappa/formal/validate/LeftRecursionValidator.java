package com.github.chrisbrenton.grappa.formal.validate;

import com.github.chrisbrenton.grappa.formal.exceptions
    .UnsupportedConstructException;
import com.github.chrisbrenton.grappa.formal.nodes.AlternationElement;
import com.github.chrisbrenton.grappa.formal.nodes.FormalRule;
import com.google.common.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class LeftRecursionValidator
    implements FormalGrammarValidator
{
    private final Set<String> leftRecursive = new HashSet<>();

    @Subscribe
    public void checkRule(final FormalRule formalRule)
    {
        final String name = formalRule.getName();

        final Set<String> firstElements = formalRule.getProductionRule()
            .getAlternations()
            .stream()
            .map(alternation -> alternation.getElements().get(0))
            .filter(element -> !element.isTerminal())
            .map(AlternationElement::getValue)
            .collect(Collectors.toSet());

        if (firstElements.contains(name))
            leftRecursive.add(name);
    }

    @Override
    public void validate()
        throws UnsupportedConstructException
    {
        if (leftRecursive.isEmpty())
            return;

        final String violations = leftRecursive.stream()
            .sorted()
            .collect(Collectors.joining(", "));

        throw new UnsupportedConstructException("unsupported rule definition(s)"
            + " (left recursive): " + violations);
    }
}
