package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.formal.NameMangler;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

import java.util.List;
import java.util.stream.Collectors;

public final class BnfProductionRule
    extends GrammarNode
{
    public BnfProductionRule(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public JExpression toExpression(final NameMangler mangler)
    {
        final List<GrammarNode> sequences = childrenStream()
            .collect(Collectors.toList());

        if (sequences.size() == 1)
            return sequences.get(0).toExpression(mangler);

        final JInvocation invocation = JExpr.invoke("firstOf");

        sequences.stream().map(node -> node.toExpression(mangler))
            .forEach(invocation::arg);

        return invocation;
    }

    @Override
    public String getValue()
    {
        return "Rule definition";
    }
}
