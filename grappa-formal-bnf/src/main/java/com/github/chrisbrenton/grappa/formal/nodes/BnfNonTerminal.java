package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.formal.ExpressionGenerator;
import com.github.chrisbrenton.grappa.formal.RuleNameMangler;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JInvocation;

import java.util.List;

public final class BnfNonTerminal
    extends ParseNode
    implements ExpressionGenerator, AlternationElement
{
    public BnfNonTerminal(final MatchTextSupplier supplier, final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public JInvocation toInvocation(final RuleNameMangler mangler)
    {
        return JExpr.invoke(mangler.fromRuleName(getValue()));
    }

    @Override
    public boolean isTerminal()
    {
        return false;
    }
}
