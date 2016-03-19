package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.nodes.Alternation;
import com.github.chrisbrenton.grappa.formal.nodes.ProductionRule;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;
import java.util.stream.Collectors;

public final class EbnfProductionRule
    extends ParseNode
    implements ProductionRule
{
    public EbnfProductionRule(final String matchedText,
        final List<ParseNode> children)
    {
        super(matchedText, children);
    }

    @Override
    public String getValue()
    {
        return "Production rule";
    }

    @Override
    public List<Alternation> getAlternations()
    {
        return children.stream()
            .map(Alternation.class::cast)
            .collect(Collectors.toList());
    }
}
