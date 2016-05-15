package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ParseNodeRepository
{
    @VisibleForTesting
    static final String MISSING_ANNOTATION = "node class %s is missing the"
        + " @NonTerminal annotation";

    @VisibleForTesting
    static final String EMPTY_NAME = "names of non terminals cannot be empty"
        + " (class %s)";

    private final Map<String, Class<?>> nodeMap;
    private final String entryPoint;

    public static Builder newBuilder()
    {
        return new Builder();
    }

    private ParseNodeRepository(final Builder builder)
    {
        nodeMap = ImmutableMap.copyOf(builder.nodeMap);
        entryPoint = builder.entryPoint;
    }

    public Map<String, Class<?>> getNodeMap()
    {
        return nodeMap;
    }

    public String getEntryPoint()
    {
        return entryPoint;
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

        @VisibleForTesting
        static final String DUPLICATE_ENTRY_POINT
            = "entry point has already been registered";

        private final Map<String, Class<?>> nodeMap = new HashMap<>();
        private String entryPoint;

        private Builder()
        {
        }

        public Builder registerNode(final Class<? extends ParseNode> nodeClass)
        {
            Objects.requireNonNull(nodeClass);

            final String name = getNonTerminalName(nodeClass);

            if (nodeMap.put(name, nodeClass) != null)
                throw new IllegalArgumentException(String.format(
                    DOUBLE_REGISTER, name));

            return this;
        }

        public Builder setRootNode(final Class<? extends ParseNode> nodeClass)
        {
            Objects.requireNonNull(nodeClass);

            final String name = getNonTerminalName(nodeClass);

            if (nodeMap.put(name, nodeClass) != null)
                throw new IllegalArgumentException(String.format(
                    DOUBLE_REGISTER, name));

            return setEntryPoint(name);
        }

        public Builder setEntryPoint(final String entryPoint)
        {
            if (this.entryPoint != null)
                throw new IllegalArgumentException(DUPLICATE_ENTRY_POINT);

            this.entryPoint = Objects.requireNonNull(entryPoint);
            return this;
        }

        public ParseNodeRepository build()
        {
            if (entryPoint == null)
                throw new IllegalArgumentException(NO_ENTRY_POINT);

            if (!nodeMap.containsKey(entryPoint))
                throw new IllegalArgumentException(String.format(
                    UNREGISTERED_ENTRY_POINT, entryPoint
                ));

            return new ParseNodeRepository(this);
        }
    }

    @VisibleForTesting
    static String getNonTerminalName(final Class<?> c)
    {
        final NonTerminal annotation = c.getAnnotation(NonTerminal.class);

        if (annotation == null)
            throw new IllegalArgumentException(String.format(MISSING_ANNOTATION,
                c.getSimpleName()));

        final String ret = annotation.value();

        if (ret.isEmpty())
            throw new IllegalArgumentException(String.format(EMPTY_NAME,
                c.getSimpleName()));

        return ret;
    }
}
