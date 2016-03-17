package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.formal.ExpressionGenerator;
import com.github.chrisbrenton.grappa.formal.RuleNameMangler;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
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
    public JExpression toExpression(final RuleNameMangler mangler)
    {
        final List<ExpressionGenerator> elements = getElements();

        if (elements.size() == 1)
            return elements.get(0).toExpression(mangler);

        final JInvocation sequence = JExpr.invoke("sequence");

        elements.stream().map(generator -> generator.toExpression(mangler))
            .forEach(sequence::arg);

        return sequence;
    }

    @Override
    public JInvocation toInvocation(final RuleNameMangler mangler)
    {
        final List<ExpressionGenerator> elements = getElements();

        if (elements.size() == 1)
            return elements.get(0).toInvocation(mangler);

        final JInvocation sequence = JExpr.invoke("sequence");

        elements.stream().map(generator -> generator.toExpression(mangler))
            .forEach(sequence::arg);

        return sequence;
    }

    @Override
    public String getValue()
    {
        return "Sequence";
    }
}
