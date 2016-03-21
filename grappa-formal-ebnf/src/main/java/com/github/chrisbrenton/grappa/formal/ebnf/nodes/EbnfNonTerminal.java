package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.nodes.AlternationElement;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

public final class EbnfNonTerminal
    extends ParseNode
    implements AlternationElement
{
    public EbnfNonTerminal(final String matchedText,
        final List<ParseNode> children)
    {
        super(matchedText, children);
    }

    @Override
    public boolean isTerminal()
    {
        return false;
    }
}