package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JInvocation;

import java.util.List;
import java.util.stream.Collectors;

public final class BnfSequence
    extends ParseNode
    implements ExpressionGenerator
{
    public BnfSequence(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }

    public List<ExpressionGenerator> getElements()
    {
        return getChildren().stream()
            .map(ExpressionGenerator.class::cast)
            .collect(Collectors.toList());
    }

    @Override
    public JInvocation toInvocation()
    {
        final List<ExpressionGenerator> elements = getElements();

        if (elements.size() == 1)
            return elements.get(0).toInvocation();

        final JInvocation sequence = JExpr.invoke("sequence");

        elements.stream().map(ExpressionGenerator::toExpression)
            .forEach(sequence::arg);

        return sequence;
    }

    @Override
    public String toString()
    {
        return "Sequence";
    }
}
