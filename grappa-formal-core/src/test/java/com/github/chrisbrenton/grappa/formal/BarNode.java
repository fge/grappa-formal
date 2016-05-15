package com.github.chrisbrenton.grappa.formal;


import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

@NonTerminal("bar")
public final class BarNode
    extends ParseNode
{
    public BarNode(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }
}
