package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

@NonTerminal("foo")
public final class FooNode1
    extends ParseNode
{
    public FooNode1(final String matchedText, final List<ParseNode> children)
    {
        super(matchedText, children);
    }
}
