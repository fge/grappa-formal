package com.github.chrisbrenton.grappa.formal;

import java.util.HashSet;
import java.util.Set;

final class NameMangler
{
    private final Set<String> generatedNames = new HashSet<>();

    public String toMethodName(final String ruleName)
    {
        final int length = ruleName.length();
        final StringBuilder sb = new StringBuilder(length);

        char c = ruleName.charAt(0);

        sb.append(Character.isJavaIdentifierStart(c) ? c : '_');

        for (int index = 1; index < length; index++) {
            c = ruleName.charAt(index);
            sb.append(Character.isJavaIdentifierPart(c) ? c : '_');
        }

        final String baseName = sb.toString();
        String ret = baseName;

        int i = 1;

        while (!generatedNames.add(ret))
            ret = baseName + i++;

        return ret;
    }
}
