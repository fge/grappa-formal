package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

public final class BnfRuleName
    extends ParseNode
{
    public BnfRuleName(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }
}
