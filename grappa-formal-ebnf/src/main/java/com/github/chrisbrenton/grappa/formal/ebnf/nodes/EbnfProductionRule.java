package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.nodes.Alternation;
import com.github.chrisbrenton.grappa.formal.nodes.ProductionRule;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;
import java.util.stream.Collectors;

public final class EbnfProductionRule
    extends ParseNode
    implements ProductionRule
{
    public EbnfProductionRule(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
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
