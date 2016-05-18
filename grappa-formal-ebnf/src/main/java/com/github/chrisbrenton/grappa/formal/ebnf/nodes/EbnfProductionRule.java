package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.NameMangler;
import com.github.chrisbrenton.grappa.formal.nodes.GrammarNode;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

import java.util.List;
import java.util.stream.Collectors;

public final class EbnfProductionRule
    extends GrammarNode
{
    public EbnfProductionRule(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public String getValue()
    {
        return "Production rule";
    }

    @Override
    public JExpression toExpression(final NameMangler mangler)
    {
        final List<GrammarNode> subRules = childrenStream()
            .collect(Collectors.toList());

        if (subRules.size() == 1)
            return subRules.get(0).toExpression(mangler);

        final JInvocation invocation = JExpr.invoke("firstOf");

        subRules.stream().map(node -> node.toExpression(mangler))
            .forEach(invocation::arg);

        return invocation;
    }
}
