package com.github.chrisbrenton.grappa.formal.ebnf;

import com.github.chrisbrenton.grappa.formal.ebnf.parser.EbnfParser;
import com.github.chrisbrenton.grappa.parsetree.listeners.ParseNodeConstructorRepository;
import com.github.chrisbrenton.grappa.parsetree.listeners.ParseTreeListener;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsetree.visual.DotFileGenerator;
import com.github.fge.grappa.run.ListeningParseRunner;
import com.github.fge.grappa.run.trace.TracingListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Example
{
    private Example()
    {
        throw new Error("no instantiation is permitted");
    }

    public static void main(final String... args)
        throws IOException, InterruptedException
    {
        final Class<EbnfParser> c = EbnfParser.class;
        final ParseNodeConstructorRepository repository
            = new ParseNodeConstructorRepository(c);

        final ParseTreeListener<Object> listener
            = new ParseTreeListener<>(repository);

        final EbnfParser parser = Grappa.createParser(c);

        final String input = loadExample();

        final ListeningParseRunner<Object> runner
            = new ListeningParseRunner<>(parser.grammar());

        runner.registerListener(listener);

        final Path trace = Paths.get("/tmp/trace.zip");
        final TracingListener<Object> tracer
            = new TracingListener<>(trace, true);

        runner.registerListener(tracer);

        if (!runner.run(input).isSuccess()) {
            System.err.println("parse error");
            System.exit(2);
        }

        final ParseNode node = listener.getRootNode();

        final Path svg = Paths.get("/tmp/ebnf.svg");

        try (
            final DotFileGenerator generator = new DotFileGenerator(svg);
        ) {
            generator.render(node);
        }
    }

    private static String loadExample()
        throws IOException
    {
        final URL url = Example.class.getResource("/example.ebnf");

        if (url == null)
            throw new IOException("unable to load example");

        final CharsetDecoder decoder = StandardCharsets.UTF_8
            .newDecoder()
            .onMalformedInput(CodingErrorAction.REPORT)
            .onUnmappableCharacter(CodingErrorAction.REPORT);

        final StringBuilder sb = new StringBuilder();
        final char[] buf = new char[2048];

        try (
            final InputStream in = url.openStream();
            final Reader reader = new InputStreamReader(in, decoder);
        ) {
            int nrChars;

            while ((nrChars = reader.read(buf)) != -1)
                sb.append(buf, 0, nrChars);
        }

        return sb.toString();
    }
}
