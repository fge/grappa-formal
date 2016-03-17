package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class BnfGrammar
    extends ParseNode
{
    public BnfGrammar(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }

    public Collection<BnfRule> getRules()
    {
        return getChildren().stream()
            .map(BnfRule.class::cast)
            .collect(Collectors.toList());
    }

    @Override
    public String getValue()
    {
        return "Grammar";
    }
}
