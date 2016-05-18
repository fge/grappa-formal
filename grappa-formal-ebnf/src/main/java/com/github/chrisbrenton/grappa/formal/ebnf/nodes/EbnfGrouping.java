package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.NameMangler;
import com.github.chrisbrenton.grappa.formal.nodes.GrammarNode;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

import java.util.List;

public final class EbnfGrouping
    extends GrammarNode
{
    public EbnfGrouping(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public String getValue()
    {
        return "Grouping";
    }

    @Override
    public JExpression toExpression(final NameMangler mangler)
    {
        final JInvocation invocation = JExpr.invoke("sequence");

        childrenStream().map(node -> node.toExpression(mangler))
            .forEach(invocation::arg);

        return invocation;
    }
}
