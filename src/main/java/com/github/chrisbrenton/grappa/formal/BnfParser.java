package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.annotations.GenerateNode;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class BnfParser
    extends BaseParser<Void>
{
    Rule nonQuotedTerminalChar()
    {
        return noneOf("\r\n'");
    }

    Rule quotedTerminalChar()
    {
        return sequence('\\', anyOf("rn'"));
    }

    Rule charTerminal()
    {
        return sequence(
            '\'',
            firstOf(nonQuotedTerminalChar(), quotedTerminalChar()),
            '\''
        );
    }

    Rule stringTerminalContent()
    {
        return join(oneOrMore(noneOf("\r\n\"")))
            .using('\\', anyOf("rn\""))
            .min(1);
    }

    Rule stringTerminal()
    {
        return sequence('"', stringTerminalContent(), '"');
    }

    @GenerateNode(TerminalNode.class)
    public Rule terminal()
    {
        return firstOf(charTerminal(), stringTerminal());
    }

    @GenerateNode(NonTerminalNode.class)
    public Rule nonTerminal()
    {
        return sequence('<', oneOrMore(alpha()), '>');
    }

    Rule assign()
    {
        return string("::=");
    }

    Rule bnfRuleRhs()
    {
        return join(firstOf(terminal(), nonTerminal()))
            .using(zeroOrMore(wsp()), '|', zeroOrMore(wsp()))
            .min(1);
    }

    @GenerateNode(BnfRuleNode.class)
    public Rule bnfRule()
    {
        return sequence(
            nonTerminal(),
            zeroOrMore(wsp()),
            assign(),
            zeroOrMore(wsp()),
            bnfRuleRhs()
        );
    }
}
