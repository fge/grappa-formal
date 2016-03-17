package com.github.chrisbrenton.grappa.formal;

import java.util.HashMap;
import java.util.Map;

public final class BnfRuleNameMangler
    implements RuleNameMangler
{
    private final Map<String, String> names = new HashMap<>();

    @Override
    public String fromRuleName(final String ruleName)
    {
        String ret = names.get(ruleName);

        if (ret != null)
            return ret;

        final int length = ruleName.length();
        final StringBuilder sb = new StringBuilder(length - 2);

        /*
         * It is assumed here that '<' starts a rule and that '>' ends it.
         *
         * Since both of these characters are in the BMP, it means we can
         * safely assume that we can read from the char at index 1 up to, and
         * excluding, the char at length - 1.
         *
         * Any character we find that is not a valid Java identifier is replaced
         * with an underscore.
         *
         * We treat the first character in a special manner since it has further
         * restrictions.
         */
        char c = ruleName.charAt(1);

        sb.append(Character.isJavaIdentifierStart(c) ? c : '_');

        for (int i = 2; i < length - 1; i++) {
            c = ruleName.charAt(i);
            sb.append(Character.isJavaIdentifierPart(c) ? c : '_');
        }

        final String baseName = sb.toString();
        ret = baseName;
        int i = 1;

        while (names.containsValue(ret))
            ret = baseName + i++;

        names.put(ruleName, ret);

        return ret;
    }
}
