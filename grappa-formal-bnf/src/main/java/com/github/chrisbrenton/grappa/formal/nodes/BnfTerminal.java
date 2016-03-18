package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.formal.ExpressionGenerator;
import com.github.chrisbrenton.grappa.formal.RuleNameMangler;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

import java.util.List;

public final class BnfTerminal
    extends ParseNode
    implements ExpressionGenerator, AlternationElement
{
    public BnfTerminal(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }

    @Override
    public JExpression toExpression(final RuleNameMangler mangler)
    {
        return matchedText.startsWith("'")
            ? JExpr.lit(unquoteChar(matchedText))
            : JExpr.lit(unquoteString(matchedText));
    }

    @Override
    public JInvocation toInvocation(final RuleNameMangler mangler)
    {
        return matchedText.startsWith("'")
            ? JExpr.invoke("ch").arg(JExpr.lit(unquoteChar(matchedText)))
            : JExpr.invoke("string").arg(JExpr.lit(unquoteString(matchedText)));
    }

    @Override
    public boolean isTerminal()
    {
        return true;
    }

    // Note: very dependent on the parsing
    private static char unquoteChar(final String input)
    {
        char c = input.charAt(1);

        if (input.length() == 3)
            return c;

        c = input.charAt(2);

        switch (c) {
            case 'r':
                return '\r';
            case 'n':
                return '\n';
            case '\'':
            case '\\':
                return c;
        }

        throw new IllegalStateException();
    }

    private static String unquoteString(final String input)
    {
        final int len = input.length() - 1;
        final StringBuilder sb = new StringBuilder(len - 1);

        boolean inBackslash = false;
        char c;

        for (int index = 1; index < len; index++) {
            c = input.charAt(index);
            if (!inBackslash) {
                if (c == '\\')
                    inBackslash = true;
                else
                    sb.append(c);
                continue;
            }
            switch (c) {
                case 'r':
                    sb.append('\r');
                    break;
                case 'n':
                    sb.append('\n');
                    break;
                case '"':
                case '\\':
                    sb.append(c);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }

        return sb.toString();
    }
}
