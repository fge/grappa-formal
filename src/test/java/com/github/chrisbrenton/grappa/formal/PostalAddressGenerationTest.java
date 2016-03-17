package com.github.chrisbrenton.grappa.formal;

import com.github.chrisbrenton.grappa.parsetree.listeners
    .ParseNodeConstructorRepository;
import com.github.chrisbrenton.grappa.parsetree.listeners.ParseTreeListener;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.run.ListeningParseRunner;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.writer.OutputStreamCodeWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

public final class PostalAddressGenerationTest
{
    private PostalAddressGenerationTest()
    {
        throw new Error("no instantiation is permitted");
    }

    public static void main(final String... args)
        throws IOException, InterruptedException
    {
        final Class<BnfParser> parserClass = BnfParser.class;

        final ParseNodeConstructorRepository repository
            = new ParseNodeConstructorRepository(parserClass);

        final BnfParser parser = Grappa.createParser(parserClass);

        final ListeningParseRunner<Void> runner
            = new ListeningParseRunner<>(parser.grammar());

        final ParseTreeListener<Void> parseTreeListener
            = new ParseTreeListener<>(repository);

        runner.registerListener(parseTreeListener);

        final String input = postalAddress();

        final boolean success = runner.run(input)
            .isSuccess();

        if (!success)
            System.exit(2);

        final ParseNode node = parseTreeListener.getRootNode();

        final BnfGenerator generator = new BnfGenerator();
        generator.visitGrammar((BnfGrammar) node);
        final JCodeModel model = generator.getModel();

        final CodeWriter writer = new OutputStreamCodeWriter(System.out,
            StandardCharsets.UTF_8.displayName());

        model.build(writer);
    }

    private static String postalAddress()
        throws IOException
    {
        final URL url = PostalAddressGenerationTest.class.getResource(
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
