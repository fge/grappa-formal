package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.formal.NameMangler;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;

import java.util.List;

public final class BnfTerminal
    extends GrammarNode
{
    private final String matchedText;
    public BnfTerminal(final MatchTextSupplier supplier, final List<ParseNode> children)
    {
        super(supplier, children);
        matchedText = supplier.getText();
    }

    @Override
    public JExpression toExpression(final NameMangler mangler)
    {
        return matchedText.startsWith("'")
            ? JExpr.lit(unquoteChar(matchedText))
            : JExpr.lit(unquoteString(matchedText));
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
