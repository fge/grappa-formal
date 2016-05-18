package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.formal.NameMangler;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

import java.util.List;
import java.util.stream.Collectors;

public final class BnfSequence
    extends GrammarNode
{
    public BnfSequence(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public JExpression toExpression(final NameMangler mangler)
    {
        final List<GrammarNode> elements = childrenStream()
            .collect(Collectors.toList());

        if (elements.size() == 1)
            return elements.get(0).toExpression(mangler);

        final JInvocation sequence = JExpr.invoke("sequence");

        elements.stream().map(node -> node.toExpression(mangler))
            .forEach(sequence::arg);

        return sequence;
    }

    @Override
    public String getValue()
    {
        return "Sequence";
    }
}
