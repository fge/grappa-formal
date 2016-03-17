package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

import java.util.List;

public final class BnfNonTerminal
    extends ParseNode
    implements ExpressionGenerator
{
    public BnfNonTerminal(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }

    @Override
    public JExpression toExpression()
    {
        return JExpr.invoke(getValue());
    }

    @Override
    public JInvocation toInvocation()
    {
        return JExpr.invoke(getValue());
    }

    @Override
    public String toString()
    {
        return getValue();
    }
}
