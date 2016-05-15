package com.github.chrisbrenton.grappa.formal.ebnf.nodes;


import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

public final class EbnfOptional
    extends ParseNode
{
    public EbnfOptional(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public String getValue()
    {
        return "Optional";
    }
}
