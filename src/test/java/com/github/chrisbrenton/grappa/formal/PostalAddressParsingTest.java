package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.listeners
    .ParseNodeConstructorRepository;
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

public final class PostalAddressParsingTest
{
    private PostalAddressParsingTest()
    {
        throw new Error("no instantiation is permitted");
    }

    public static void main(final String... args)
        throws IOException, InterruptedException
    {
        final Path outfile = Paths.get("bnfTest.svg");
        final Path traceFile = Paths.get("trace.zip");

        final Class<BnfParser> parserClass = BnfParser.class;

        final ParseNodeConstructorRepository repository
            = new ParseNodeConstructorRepository(parserClass);

        final BnfParser parser = Grappa.createParser(parserClass);

        final ListeningParseRunner<Void> runner
            = new ListeningParseRunner<>(parser.grammar());

        final ParseTreeListener<Void> parseTreeListener
            = new ParseTreeListener<>(repository);
        final TracingListener<Void> tracingListener
            = new TracingListener<>(traceFile, true);

        runner.registerListener(parseTreeListener);
        runner.registerListener(tracingListener);

        final String input = postalAddress();

        final boolean success = runner.run(input)
            .isSuccess();

        if (!success)
            System.exit(2);

        final ParseNode node = parseTreeListener.getRootNode();

        try (
            final DotFileGenerator generator
                = new DotFileGenerator(outfile, ParseNode::getValue);
        ) {
            generator.render(node);
        }
    }

    private static String postalAddress()
        throws IOException
    {
        final URL url = PostalAddressParsingTest.class.getResource(
            "/postalAddress.bnf");

        if (url == null)
            throw new IOException("unable to load example file");

        final StringBuilder sb = new StringBuilder();

        final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
            .onUnmappableCharacter(CodingErrorAction.REPORT)
            .onMalformedInput(CodingErrorAction.REPORT);

        try (
            final InputStream in = url.openStream();
            final Reader reader = new InputStreamReader(in, decoder);
        ) {
            final char[] buf = new char[2048];
            int nrChars;

            while ((nrChars = reader.read(buf)) != -1)
                sb.append(buf, 0, nrChars);
        }

        return sb.toString();
    }
}
