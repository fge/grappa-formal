package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.formal.ExpressionGenerator;
import com.github.chrisbrenton.grappa.formal.RuleNameMangler;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JInvocation;

import java.util.List;
import java.util.stream.Collectors;

public final class BnfProductionRule
    extends ParseNode
    implements ExpressionGenerator, ProductionRule
{
    public BnfProductionRule(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }

    public List<BnfSequence> getSequences()
    {
        return getChildren().stream()
            .map(BnfSequence.class::cast)
            .collect(Collectors.toList());
    }

    @Override
    public List<Alternation> getAlternations()
    {
        return getChildren().stream()
            .map(Alternation.class::cast)
            .collect(Collectors.toList());
    }

    @Override
    public JInvocation toInvocation(final RuleNameMangler mangler)
    {
        final List<BnfSequence> sequences = getSequences();

        if (sequences.size() == 1)
            return sequences.get(0).toInvocation(mangler);

        final JInvocation invocation = JExpr.invoke("firstOf");

        sequences.stream().map(generator -> generator.toExpression(mangler))
            .forEach(invocation::arg);

        return invocation;
    }

    @Override
    public String getValue()
    {
        return "Rule definition";
    }
}
