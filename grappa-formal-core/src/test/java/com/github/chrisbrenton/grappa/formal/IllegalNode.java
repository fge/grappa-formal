package com.github.chrisbrenton.grappa.formal;


import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

@NonTerminal("")
public final class IllegalNode
    extends ParseNode
{
    public IllegalNode(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }
}
