package com.github.chrisbrenton.grappa.formal.nodes;


import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

public final class BnfRule
    extends ParseNode
    implements FormalRule
{
    public BnfRule(final MatchTextSupplier supplier, final List<ParseNode> children)
    {
        super(supplier, children);
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
