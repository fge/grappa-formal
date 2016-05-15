package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.nodes.AlternationElement;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

public final class EbnfLiteral
    extends ParseNode
    implements AlternationElement
{
    public EbnfLiteral(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public boolean isTerminal()
    {
        return true;
    }
}
