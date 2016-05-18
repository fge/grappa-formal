package com.github.chrisbrenton.grappa.formal.nodes;

import com.github.chrisbrenton.grappa.formal.NameMangler;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.sun.codemodel.JExpression;

import java.util.List;
import java.util.stream.Stream;

public abstract class GrammarNode
    extends ParseNode
{
    /**
     * Base constructor
     *
     * @param supplier the supplier of text matched by this node
     * @param children the children of this node
     */
    protected GrammarNode(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    public final boolean isTerminal()
    {
        final int nrChildren = children.size();

        switch (nrChildren) {
            case 0:
                return true;
            case 1:
                return ((GrammarNode) children.get(0)).isTerminal();
            default:
                return false;
        }
    }

    public abstract JExpression toExpression(NameMangler mangler);

    protected final Stream<GrammarNode> childrenStream()
    {
        return children.stream().map(node -> (GrammarNode) node);
    }
}
