package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.formal.exceptions.FormalismException;
import com.github.chrisbrenton.grappa.formal.validate.FormalGrammarValidator;
import com.github.chrisbrenton.grappa.formal.validate.LeftRecursionValidator;
import com.github.chrisbrenton.grappa.parsetree.build
    .ParseNodeConstructorProvider;
import com.github.chrisbrenton.grappa.parsetree.build.ParseTreeBuilder;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.chrisbrenton.grappa.parsetree.visit.VisitorRunner;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsetree.visual.DotFileGenerator;
import com.github.fge.grappa.run.ParseRunner;
import com.github.fge.grappa.run.trace.TracingListener;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class BnfParsingTest
{
    private BnfParsingTest()
    {
        throw new Error("no instantiation is permitted");
    }

    public static void main(final String... args)
        throws IOException, InterruptedException, FormalismException
    {
        final Path outfile = Paths.get("bnfTest.svg");
        final Path traceFile = Paths.get("trace.zip");

        final Class<BnfParser> parserClass = BnfParser.class;

        final ParseNodeConstructorProvider provider
            = new ParseNodeConstructorProvider(parserClass);

        final BnfParser parser = Grappa.createParser(parserClass);

        final ParseRunner<Void> runner
            = new ParseRunner<>(parser.grammar());

        final ParseTreeBuilder<Void> parseTreeListener
            = new ParseTreeBuilder<>(provider);
        final TracingListener<Void> tracingListener
            = new TracingListener<>(traceFile, true);

        runner.registerListener(parseTreeListener);
        runner.registerListener(tracingListener);

        final boolean success = runner.run("<FOO> ::= 'a' 'b' | <e>")
            .isSuccess();

        if (!success)
            System.exit(2);

        final ParseNode node = parseTreeListener.getTree();

        final VisitorRunner visitorRunner = new VisitorRunner(node);

        final FormalGrammarValidator validator = new LeftRecursionValidator();

        visitorRunner.registerVisitor(validator);
        visitorRunner.run();

        validator.validate();

        try (
            final DotFileGenerator generator
                = new DotFileGenerator(outfile, ParseNode::toString);
        ) {
            generator.render(node);
        }
    }
}
