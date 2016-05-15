package com.github.chrisbrenton.grappa.formal.ebnf.nodes;

import com.github.chrisbrenton.grappa.formal.nodes.Alternation;
import com.github.chrisbrenton.grappa.formal.nodes.AlternationElement;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EbnfAlternation
    extends ParseNode
    implements Alternation
{
    private final List<AlternationElement> elements = new ArrayList<>();
    public EbnfAlternation(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
        children.stream().forEach(this::fillElementList);
    }

    @Override
    public String getValue()
    {
        return "Alternation";
    }

    @Override
    public List<AlternationElement> getElements()
    {
        return Collections.unmodifiableList(elements);
    }

    private void fillElementList(final ParseNode node)
    {
        if (!node.hasChildren())
            elements.add((AlternationElement) node);
        else
            node.getChildren().forEach(this::fillElementList);
    }
}
