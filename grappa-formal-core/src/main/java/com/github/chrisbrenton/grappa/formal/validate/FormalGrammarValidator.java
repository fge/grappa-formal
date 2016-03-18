package com.github.chrisbrenton.grappa.formal.validate;

import com.github.chrisbrenton.grappa.formal.exceptions.FormalismException;
import com.github.chrisbrenton.grappa.parsetree.visitors.Visitor;

public interface FormalGrammarValidator
    extends Visitor
{
    void validate()
        throws FormalismException;
}
