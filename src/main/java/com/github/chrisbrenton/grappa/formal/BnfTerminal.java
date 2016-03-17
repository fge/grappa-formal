package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

import java.util.List;

public final class BnfTerminal
    extends ParseNode
    implements ExpressionGenerator
{
    public BnfTerminal(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }

    @Override
    public JExpression toExpression()
    {
        return JExpr.lit(getValue());
    }

    @Override
    public JInvocation toInvocation()
    {
        final String value = getValue();
        return value.startsWith("'")
            ? JExpr.invoke("ch").arg(value)
            : JExpr.invoke("string").arg(value);
    }

    @Override
    public String toString()
    {
        return getValue();
    }
}
