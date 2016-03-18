package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

public final class BnfRule
    extends ParseNode
    implements FormalRule
{
    public BnfRule(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }

    @Override
    public String getName()
    {
        return getChildren().get(0).getValue();
    }

    @Override
    public BnfProductionRule getProductionRule()
    {
        return BnfProductionRule.class.cast(getChildren().get(1));
    }

    @Override
    public String getValue()
    {
        return "Rule";
    }
}
