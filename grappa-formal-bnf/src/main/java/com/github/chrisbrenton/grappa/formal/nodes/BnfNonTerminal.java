package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.formal.NameMangler;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;

import java.util.List;

public final class BnfNonTerminal
    extends GrammarNode
{
    public BnfNonTerminal(final MatchTextSupplier supplier, final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public JExpression toExpression(final NameMangler mangler)
    {
        return JExpr.invoke(mangler.toMethodName(getValue()));
    }
}
