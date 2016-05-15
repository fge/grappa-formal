package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.nodes.FormalRule;
import com.github.chrisbrenton.grappa.formal.nodes.ProductionRule;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

public final class EbnfFormalRule
    extends ParseNode
    implements FormalRule
{
    public EbnfFormalRule(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public String getValue()
    {
        return "Rule";
    }

    @Override
    public String getName()
    {
        return children.get(0).getValue();
    }

    @Override
    public ProductionRule getProductionRule()
    {
        return (ProductionRule) children.get(1);
    }
}
