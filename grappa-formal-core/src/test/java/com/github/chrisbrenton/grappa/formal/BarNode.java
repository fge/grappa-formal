package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

@NonTerminal("bar")
public final class BarNode
    extends ParseNode
{
    public BarNode(final String matchedText, final List<ParseNode> children)
    {
        super(matchedText, children);
    }
}
