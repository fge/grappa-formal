package com.github.chrisbrenton.grappa.formal.ebnf.nodes;


import com.github.chrisbrenton.grappa.formal.NameMangler;
import com.github.chrisbrenton.grappa.formal.nodes.GrammarNode;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

import java.util.List;

public final class EbnfOptional
    extends GrammarNode
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

    @Override
    public JExpression toExpression(final NameMangler mangler)
    {
        final JInvocation invocation = JExpr.invoke("optional");

        final JExpression arg = ((GrammarNode) getChildren().get(0))
            .toExpression(mangler);

        return invocation.arg(arg);
    }
}
