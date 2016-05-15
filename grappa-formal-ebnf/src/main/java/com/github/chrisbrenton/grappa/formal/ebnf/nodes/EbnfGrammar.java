package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.nodes.FormalGrammar;
import com.github.chrisbrenton.grappa.formal.nodes.FormalRule;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;
import java.util.stream.Collectors;

public final class EbnfGrammar
    extends ParseNode
    implements FormalGrammar
{
    public EbnfGrammar(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public String getValue()
    {
        return "Grammar";
    }

    @Override
    public List<FormalRule> getFormalRules()
    {
        return children.stream()
            .map(FormalRule.class::cast)
            .collect(Collectors.toList());
    }
}
