package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

public final class BnfSequence
    extends ParseNode
{
    public BnfSequence(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }

    @Override
    public String toString()
    {
        return "Sequence";
    }
}
