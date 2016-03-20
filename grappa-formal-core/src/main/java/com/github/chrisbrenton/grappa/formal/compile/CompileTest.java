package com.github.chrisbrenton.grappa.formal.compile;

import com.github.chrisbrenton.grappa.formal.FormalGrammarParser;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.google.common.collect.ImmutableList;
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

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Locale;

public final class CompileTest
{
    private static final JavaCompiler COMPILER
        = ToolProvider.getSystemJavaCompiler();

    public static void main(final String... args)
        throws JClassAlreadyExistsException, IOException, URISyntaxException,
        ClassNotFoundException
    {
        /*
         * First, generate some code of a _valid_ parser
         */
        final JCodeModel model = new JCodeModel();

        final JClass e = model.ref(BaseParser.class).narrow(Object.class);

        final String pkgName = "foo.bar";
        final String className = "Baz";
        final String fqdn = "foo.bar.Baz";
        final JDefinedClass c = model._class(fqdn);
        c._extends(FormalGrammarParser.class);

        final JMethod method = c.method(JMod.PUBLIC, Rule.class, "entryPoint");
        method.annotate(Override.class);

        final JBlock body = method.body();
        body._return(JExpr.invoke("oneOrMore").arg("x"));

        final String s;
        try (
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            final CodeWriter cw = new OutputStreamCodeWriter(out,
                StandardCharsets.UTF_8.displayName());
            model.build(cw);
            s = new String(out.toByteArray(), StandardCharsets.UTF_8);
        }

        System.out.println(s);

        final JavaFileObject obj = new FromStringFileObject(fqdn,
            s);

        final StandardJavaFileManager manager = COMPILER
            .getStandardFileManager(System.out::println,
                Locale.ENGLISH, StandardCharsets.UTF_8);

        final Path tmpdir = Files.createTempDirectory("comp");

        final URL[] classPath = { tmpdir.toUri().toURL() };
        final URLClassLoader cl = URLClassLoader.newInstance(classPath);

        try (
            StringWriter compilerOut = new StringWriter();
        ) {
            final JavaCompiler.CompilationTask task = COMPILER
                .getTask(compilerOut, manager, System.out::println,
                    ImmutableList.of("-d", tmpdir.toString()),
                    null, Collections.singletonList(obj));
            if (!task.call())
                System.exit(2);
        }


        final Class<? extends FormalGrammarParser> compiled
            = (Class<? extends FormalGrammarParser>) cl.loadClass(fqdn);

        // Fails here; that was unfortunately expected.
        Grappa.createParser(compiled);
    }
}
