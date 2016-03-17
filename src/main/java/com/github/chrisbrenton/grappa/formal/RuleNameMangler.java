package com.github.chrisbrenton.grappa.formal;

@FunctionalInterface
public interface RuleNameMangler
{
    String fromRuleName(String ruleName);
}
