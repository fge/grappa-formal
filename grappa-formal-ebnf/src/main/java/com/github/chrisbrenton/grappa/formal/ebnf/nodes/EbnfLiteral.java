package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.NameMangler;
import com.github.chrisbrenton.grappa.formal.nodes.GrammarNode;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;

import java.util.List;

public final class EbnfLiteral
    extends GrammarNode
{
    public EbnfLiteral(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public JExpression toExpression(final NameMangler mangler)
    {
        final String text = getMatchedText();
        final String content = text.substring(1, text.length() - 1);
        return JExpr.lit(content);
    }
}
