package com.github.chrisbrenton.grappa.formal;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;

public final class ParseNodeRepositoryTest
{
    @Test
    public void noNonTerminalAnnotationIsIllegal()
    {
        try {
            // We don't care whether this is not a node class; the filter
            // happens in the builder, not here.
            ParseNodeRepository.getNonTerminalName(Object.class);
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(String.format(
                ParseNodeRepository.MISSING_ANNOTATION,
                Object.class.getSimpleName()));
        }
    }

    @Test
    public void emptyNonTerminalIsIllegal()
    {
        try {
            ParseNodeRepository.getNonTerminalName(IllegalNode.class);
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(String.format(ParseNodeRepository.EMPTY_NAME,
                IllegalNode.class.getSimpleName()));
        }
    }

    @Test
    public void doubleRegisterNodeIsIllegal()
    {
        try {
            ParseNodeRepository.newBuilder()
                .registerNode(FooNode1.class)
                .registerNode(FooNode2.class);
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(String.format(
                ParseNodeRepository.Builder.DOUBLE_REGISTER, "foo"
            ));
        }
    }

    @Test
    public void doubleEntryPointIsIllegal()
    {
        try {
            ParseNodeRepository.newBuilder()
                .registerNode(FooNode1.class)
                .setEntryPoint("foo")
                .setEntryPoint("foo");
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(ParseNodeRepository.Builder
                .DUPLICATE_ENTRY_POINT);
        }
    }

    @Test
    public void mustSetEntryPoint()
    {
        try {
            ParseNodeRepository.newBuilder().build();
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(ParseNodeRepository.Builder.NO_ENTRY_POINT);
        }
    }

    @Test
    public void entryPointMustBeRegistered()
    {
        try {
            ParseNodeRepository.newBuilder()
                .registerNode(FooNode1.class)
                .setEntryPoint("bar")
                .build();
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(String.format(
                ParseNodeRepository.Builder.UNREGISTERED_ENTRY_POINT, "bar"
            ));

        }
    }

    @Test
    public void forbidBothRootNodeAndEntryPoint()
    {
        try {
            ParseNodeRepository.newBuilder()
                .setRootNode(FooNode1.class)
                .setEntryPoint("foo");
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(ParseNodeRepository.Builder
                .DUPLICATE_ENTRY_POINT);
        }
    }
}
