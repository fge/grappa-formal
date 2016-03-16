package com.github.chrisbrenton.grappa.formal;

import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.writer.OutputStreamCodeWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class CodeModelTest
{
    private CodeModelTest()
    {
        throw new Error("no instantiation is permitted");
    }

    public static void main(final String... args)
        throws JClassAlreadyExistsException, IOException
    {
        final JCodeModel model = new JCodeModel();

        final JClass e = model.ref(BaseParser.class).narrow(Object.class);

        final JDefinedClass c = model._class("foo.bar.Baz");
        c._extends(e);

        final JMethod method = c.method(JMod.PUBLIC, Rule.class, "someRule");

        final JBlock body = method.body();
        body._return(JExpr.invoke("foo").arg("x").arg(JExpr.invoke("bar")));

        final CodeWriter cw = new OutputStreamCodeWriter(System.out,
            StandardCharsets.UTF_8.displayName());

        model.build(cw);
    }
}
