package com.github.chrisbrenton.grappa.formal;

import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

@FunctionalInterface
public interface ExpressionGenerator
{
    default JExpression toExpression()
    {
        return toInvocation();
    }

    JInvocation toInvocation();
}
