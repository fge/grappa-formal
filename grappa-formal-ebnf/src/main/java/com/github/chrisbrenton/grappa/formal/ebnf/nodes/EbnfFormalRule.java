package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.nodes.FormalRule;
import com.github.chrisbrenton.grappa.formal.nodes.ProductionRule;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

public final class EbnfFormalRule
    extends ParseNode
    implements FormalRule
{
    public EbnfFormalRule(final String matchedText,
        final List<ParseNode> children)
    {
        super(matchedText, children);
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
