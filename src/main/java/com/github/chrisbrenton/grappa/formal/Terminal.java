package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

public final class Terminal
    extends ParseNode
{
    public Terminal(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }

    @Override
    public String toString()
    {
        return getValue();
    }
}
