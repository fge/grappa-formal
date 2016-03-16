## README FIRST

The license of this package is ASL 2.0. See the LICENSE file at the root of this
repository for more details.

### Status...

In development.

## What this is

This package has two goals:

* from grammar formalisms (such as
  [BNF](https://en.wikipedia.org/wiki/Backus%E2%80%93Naur_Form) and derivates,
  [WSN](https://en.wikipedia.org/wiki/Wirth_syntax_notation), [attribute
  grammars](https://en.wikipedia.org/wiki/Attribute_grammar), others?),
  generate Grappa parsers;
* the reverse: given a Grappa parser, generate a formalism of your choice.

The goal is for each of these transformations to happen _at runtime_.

## How?

### Formalisms to Grappa

The intended process is as follows:

* have a... Grappa parser... written for a given formalism;
* generate a comprehensive representation of that formalism;
* from this representation, generate the source of the parser, using
  [JCodeModel](https://github.com/UnquietCode/JCodeModel);
* use the JDK compiler API to compile this generated source into byte code;
* use the Grappa facilities to generate a parser.

### Grappa to formalism

This part requires that Grappa provide, for each of the rules, a formal
description at runtime. This is a work in progress, but it is necessary in order
to achieve this goal.

Once this work is done, then the plan will be to:

* generate the parser,
* collect the rules,
* collect the formal description for each rule,
* generate the formalism.
