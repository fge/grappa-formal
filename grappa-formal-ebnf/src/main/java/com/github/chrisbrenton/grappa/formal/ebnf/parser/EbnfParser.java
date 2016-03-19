package com.github.chrisbrenton.grappa.formal.ebnf.parser;

import com.github.chrisbrenton.grappa.formal.ebnf.nodes.EbnfAlternation;
import com.github.chrisbrenton.grappa.formal.ebnf.nodes.EbnfFormalRule;
import com.github.chrisbrenton.grappa.formal.ebnf.nodes.EbnfGrammar;
import com.github.chrisbrenton.grappa.formal.ebnf.nodes.EbnfLiteral;
import com.github.chrisbrenton.grappa.formal.ebnf.nodes.EbnfNonTerminal;
import com.github.chrisbrenton.grappa.formal.ebnf.nodes.EbnfOptional;
import com.github.chrisbrenton.grappa.formal.ebnf.nodes.EbnfProductionRule;
import com.github.chrisbrenton.grappa.formal.ebnf.nodes.EbnfRepetition;
import com.github.chrisbrenton.grappa.parsetree.annotations.GenerateNode;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

@SuppressWarnings("WeakerAccess")
public class EbnfParser
    extends BaseParser<Void>
{
    Rule comment()
    {
        return sequence(
            "(*",
            join(oneOrMore(noneOf("*"))).using('*', testNot(')')).min(0),
            "*)"
        );
    }

    Rule spnlsp()
    {
        return join(zeroOrMore(wsp()))
            .using(optional(cr()), lf())
            .min(1);
    }

    Rule doubleQuotedString()
    {
        return sequence('"', oneOrMore(noneOf("\"")), '"');
    }

    Rule singleQuotedString()
    {
        return sequence('\'', oneOrMore(noneOf("'")), '\'');
    }

    @GenerateNode(EbnfLiteral.class)
    public Rule literal()
    {
        return firstOf(doubleQuotedString(), singleQuotedString());
    }

    @GenerateNode(EbnfNonTerminal.class)
    public Rule nonTerminal()
    {
        return join(oneOrMore(alpha()))
            .using(oneOrMore(wsp()))
            .min(1);
    }

    @GenerateNode(EbnfRepetition.class)
    public Rule repetition()
    {
        return sequence(
            '{',
            zeroOrMore(wsp()),
            ruleBody(),
            zeroOrMore(wsp()),
            '}'
        );
    }

    @GenerateNode(EbnfOptional.class)
    public Rule optionalMember()
    {
        return sequence(
            '[',
            zeroOrMore(wsp()),
            ruleBody(),
            zeroOrMore(wsp()),
            ']'
        );
    }

    public Rule grouping()
    {
        return sequence(
            '(',
            zeroOrMore(wsp()),
            ruleBody(),
            zeroOrMore(wsp()),
            ')'
        );
    }

    @GenerateNode(EbnfAlternation.class)
    public Rule alternation()
    {
        return join(sequenceMember())
            .using(zeroOrMore(wsp()), ',', zeroOrMore(wsp()))
            .min(1);
    }

    public Rule ruleBody()
    {
        return join(alternation()).using(spnlsp(), '|', spnlsp()).min(1);
    }

    @GenerateNode(EbnfProductionRule.class)
    public Rule productionRule()
    {
        return ruleBody();
    }

    @GenerateNode(EbnfFormalRule.class)
    public Rule formalRule()
    {
        return sequence(
            nonTerminal(),
            zeroOrMore(wsp()),
            '=',
            zeroOrMore(wsp()),
            productionRule(),
            zeroOrMore(wsp()),
            ';'
        );
    }

    public Rule sequenceMember()
    {
        return firstOf(
            literal(),
            nonTerminal(),
            repetition(),
            optionalMember(),
            grouping()
        );
    }

    Rule betweenRules()
    {
        return join(spnlsp())
            .using(comment())
            .min(1);
    }

    Rule ruleSeries()
    {
        return join(formalRule())
            .using(betweenRules())
            .min(1);
    }

    Rule beforeRules()
    {
        return zeroOrMore(
            zeroOrMore(wsp()),
            optional(comment(), zeroOrMore(wsp())),
            optional(cr()),
            lf()
        );
    }

    Rule afterRules()
    {
        return beforeRules();
    }

    @GenerateNode(EbnfGrammar.class)
    public Rule grammar()
    {
        return sequence(
            beforeRules(),
            ruleSeries(),
            afterRules(),
            EOI
        );
    }
}
