package com.github.chrisbrenton.grappa.formal.validate;

import com.github.chrisbrenton.grappa.formal.exceptions.DuplicateRulesException;
import com.github.chrisbrenton.grappa.formal.nodes.FormalRule;
import com.google.common.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class DuplicateRuleNamesValidator
    implements FormalGrammarValidator
{
    private final Set<String> names = new HashSet<>();
    private final Set<String> duplicates = new HashSet<>();

    @Subscribe
    public void collectRuleNames(final FormalRule rule)
    {
        final String name = rule.getName();

        if (!names.add(name))
            duplicates.add(name);
    }

    @Override
    public void validate()
        throws DuplicateRulesException
    {
        if (duplicates.isEmpty())
            return;

        final String violations = duplicates.stream()
            .sorted()
            .collect(Collectors.joining(", "));

        throw new DuplicateRulesException("duplicate rule(s): " + violations);
    }
}
