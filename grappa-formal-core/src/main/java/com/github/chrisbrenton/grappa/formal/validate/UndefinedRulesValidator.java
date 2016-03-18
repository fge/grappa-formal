package com.github.chrisbrenton.grappa.formal.validate;

import com.github.chrisbrenton.grappa.formal.exceptions.UndefinedRulesException;
import com.github.chrisbrenton.grappa.formal.nodes.AlternationElement;
import com.github.chrisbrenton.grappa.formal.nodes.FormalRule;
import com.google.common.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class UndefinedRulesValidator
    implements FormalGrammarValidator
{
    private final Set<String> names = new HashSet<>();
    private final Set<String> references = new HashSet<>();

    @Subscribe
    public void collectNames(final FormalRule rule)
    {
        names.add(rule.getName());
    }

    @Subscribe
    public void collectReferences(final AlternationElement element)
    {
        if (!element.isTerminal())
            references.add(element.getValue());
    }

    @Override
    public void validate()
        throws UndefinedRulesException
    {
        references.removeAll(names);

        if (references.isEmpty())
            return;

        final String violations = references.stream()
            .sorted()
            .collect(Collectors.joining(", "));

        throw new UndefinedRulesException("the following rule(s) is/are not"
            + " defined: " + violations);
    }
}
