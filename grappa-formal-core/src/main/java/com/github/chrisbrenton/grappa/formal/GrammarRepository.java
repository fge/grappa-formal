package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class GrammarRepository
{
    private final Map<String, Class<?>> nodeMap;
    private final String entryPoint;

    public static Builder newBuilder()
    {
        return new Builder();
    }

    private GrammarRepository(final Builder builder)
    {
        nodeMap = ImmutableMap.copyOf(builder.nodeMap);
        entryPoint = builder.entryPoint;
    }

    public static final class Builder
    {
        @VisibleForTesting
        static final String DOUBLE_REGISTER
            = "non terminal %s is already registered";

        @VisibleForTesting
        static final String NO_ENTRY_POINT = "entry point was not defined"
            + " (please use .setEntryPoint())";

        @VisibleForTesting
        static final String UNREGISTERED_ENTRY_POINT
            = "entry point %s has not been registered";

        private final Map<String, Class<?>> nodeMap = new HashMap<>();
        private String entryPoint;

        private Builder()
        {
        }

        public Builder registerNonTerminal(final String nonTerminal,
            final Class<? extends ParseNode> nodeClass)
        {
            Objects.requireNonNull(nonTerminal);
            Objects.requireNonNull(nodeClass);

            if (nodeMap.put(nonTerminal, nodeClass) != null)
                throw new IllegalArgumentException(String.format(
                    DOUBLE_REGISTER, nonTerminal));

            return this;
        }

        public Builder setEntryPoint(final String entryPoint)
        {
            this.entryPoint = Objects.requireNonNull(entryPoint);
            return this;
        }

        public GrammarRepository build()
        {
            if (entryPoint == null)
                throw new IllegalArgumentException(NO_ENTRY_POINT);

            if (!nodeMap.containsKey(entryPoint))
                throw new IllegalArgumentException(String.format(
                    UNREGISTERED_ENTRY_POINT, entryPoint
                ));

            return new GrammarRepository(this);
        }
    }
}
