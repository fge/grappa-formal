package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

public final class BnfRule
    extends ParseNode
{
    public BnfRule(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }

    public String getName()
    {
        return getChildren().get(0).getValue();
    }

    public BnfRuleDefinition getDefinition()
    {
        return BnfRuleDefinition.class.cast(getChildren().get(1));
    }

    @Override
    public String getValue()
    {
        return "Rule";
    }
}
