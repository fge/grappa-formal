package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

@NonTerminal("foo")
public final class FooNode2
    extends ParseNode
{
    public FooNode2(final String matchedText, final List<ParseNode> children)
    {
        super(matchedText, children);
    }
}
