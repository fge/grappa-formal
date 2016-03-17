package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.formal.nodes.BnfGrammar;
import com.github.chrisbrenton.grappa.formal.nodes.BnfRule;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.google.common.eventbus.Subscribe;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;

public final class BnfGenerator
{
    private final JCodeModel model = new JCodeModel();
    private final JDefinedClass definedClass;

    private final RuleNameMangler mangler = new BnfRuleNameMangler();

    public BnfGenerator()
    {
        final JClass extended = model.ref(BaseParser.class)
            .narrow(Object.class);

        try {
            definedClass = model._class("foo.bar.MyParser")._extends(extended);
        } catch (JClassAlreadyExistsException e) {
            throw new IllegalStateException("How did I even get here??", e);
        }
    }

    @Subscribe
    public void visitGrammar(final BnfGrammar grammar)
    {
        grammar.getRules().forEach(this::addRule);
    }

    private void addRule(final BnfRule rule)
    {
        final String label = rule.getName();
        final String methodName = mangler.fromRuleName(rule.getName());

        final JMethod method = definedClass.method(JMod.PUBLIC, Rule.class,
            methodName);
        // The generated code automatically collapses "value" if it's the only
        // parameter of the annotation
        method.annotate(Label.class).param("value", label);
        method.body()._return(rule.getDefinition().toExpression(mangler));
    }

    public JCodeModel getModel()
    {
        return model;
    }
}
