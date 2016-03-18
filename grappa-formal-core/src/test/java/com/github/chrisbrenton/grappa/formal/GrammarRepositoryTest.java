package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;

public final class GrammarRepositoryTest
{
    @Test
    public void doubleRegisterNotAllowed()
    {
        try {
            GrammarRepository.newBuilder()
                .registerNonTerminal("foo", ParseNode.class)
                .registerNonTerminal("foo", ParseNode.class);
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(String.format(
                GrammarRepository.Builder.DOUBLE_REGISTER, "foo"
            ));
        }
    }

    @Test
    public void mustSetEntryPoint()
    {
        try {
            GrammarRepository.newBuilder().build();
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(GrammarRepository.Builder.NO_ENTRY_POINT);
        }
    }

    @Test
    public void entryPointMustBeRegistered()
    {
        try {
            GrammarRepository.newBuilder()
                .registerNonTerminal("foo", ParseNode.class)
                .setEntryPoint("bar")
                .build();
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(String.format(
                GrammarRepository.Builder.UNREGISTERED_ENTRY_POINT, "bar"
            ));

    }
    }
}
