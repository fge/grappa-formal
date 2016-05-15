package com.github.chrisbrenton.grappa.formal;


import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

@NonTerminal("foo")
public final class FooNode2
    extends ParseNode
{
    public FooNode2(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }
}
