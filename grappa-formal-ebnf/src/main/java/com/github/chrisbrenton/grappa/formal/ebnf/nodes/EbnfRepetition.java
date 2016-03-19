package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

public final class EbnfRepetition
    extends ParseNode
{
    public EbnfRepetition(final String matchedText,
        final List<ParseNode> children)
    {
        super(matchedText, children);
    }

    @Override
    public String getValue()
    {
        return "Repeat";
    }
}
