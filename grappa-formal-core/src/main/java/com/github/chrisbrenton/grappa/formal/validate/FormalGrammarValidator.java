package com.github.chrisbrenton.grappa.formal.validate;

import com.github.chrisbrenton.grappa.formal.exceptions.FormalismException;
import com.github.chrisbrenton.grappa.parsetree.visit.Visitor;

public interface FormalGrammarValidator
    extends Visitor
{
    void validate()
        throws FormalismException;
}
