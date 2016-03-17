package com.github.chrisbrenton.grappa.formal;

import com.github.fge.grappa.rules.Rule;

@FunctionalInterface
public interface GrammarEntryPoint
{
    Rule grammar();
}
