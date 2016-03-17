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
        return sequence('"', optional(stringTerminalContent()), '"');
    }

    @GenerateNode(BnfTerminal.class)
    public Rule terminal()
    {
        return firstOf(charTerminal(), stringTerminal());
    }

    @GenerateNode(BnfNonTerminal.class)
    public Rule nonTerminal()
    {
        return sequence('<', oneOrMore(noneOf("\r\n>")), '>');
    }

    Rule assign()
    {
        return string("::=");
    }

    @GenerateNode(BnfSequence.class)
    public Rule bnfRuleSequence()
    {
        return join(firstOf(terminal(), nonTerminal()))
            .using(oneOrMore(wsp()))
            .min(1);
    }

    @GenerateNode(BnfRuleName.class)
    public Rule ruleName()
    {
        return nonTerminal();
    }

    @GenerateNode(BnfRuleDefinition.class)
    public Rule ruleDefinition()
    {
        return join(bnfRuleSequence())
            .using(
                zeroOrMore(wsp()),
                optional(nl(), zeroOrMore(wsp())),
                '|',
                zeroOrMore(wsp())
            )
            .min(1);
    }

    @GenerateNode(BnfRule.class)
    public Rule bnfRule()
    {
        return sequence(
            ruleName(),
            zeroOrMore(wsp()),
            assign(),
            zeroOrMore(wsp()),
            ruleDefinition()
        );
    }

    Rule nl()
    {
        return sequence(optional(cr()), lf());
    }

    Rule betweenRules()
    {
        return sequence(
            zeroOrMore(wsp()),
            nl(),
            join(zeroOrMore(wsp())).using(nl()).min(0),
            zeroOrMore(wsp())
        );
    }

    Rule afterGrammar()
    {
        return optional(betweenRules());
    }

    @GenerateNode(BnfGrammar.class)
    public Rule grammar()
    {
        return sequence(
            zeroOrMore(wsp()),
            join(bnfRule()).using(betweenRules()).min(1),
            afterGrammar(),
            EOI
        );
    }
}
