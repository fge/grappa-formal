package com.github.chrisbrenton.grappa.formal;

import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public abstract class FormalGrammarParser
    extends BaseParser<Object>
{
    public abstract Rule entryPoint();
}
