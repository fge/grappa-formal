package com.github.chrisbrenton.grappa.formal;

import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

@FunctionalInterface
public interface ExpressionGenerator
{
    default JExpression toExpression(RuleNameMangler mangler)
    {
        return toInvocation(mangler);
    }

    JInvocation toInvocation(RuleNameMangler mangler);
}
