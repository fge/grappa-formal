package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

@NonTerminal("")
public final class IllegalNode
    extends ParseNode
{
    public IllegalNode(final String matchedText, final List<ParseNode> children)
    {
        super(matchedText, children);
    }
}
