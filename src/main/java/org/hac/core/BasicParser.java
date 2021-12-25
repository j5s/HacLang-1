package org.hac.core;

import org.hac.ast.*;
import org.hac.exception.ParseException;
import org.hac.lexer.Lexer;
import org.hac.parser.Operators;
import org.hac.parser.Parser;
import org.hac.token.Token;

import java.util.HashSet;

public class BasicParser {
    HashSet<String> reserved = new HashSet<>();
    Operators operators = new Operators();
    Parser expr0 = Parser.rule();
    Parser primary = Parser.rule(PrimaryExpr.class)
            .or(Parser.rule().sep("(").ast(expr0).sep(")"),
                    Parser.rule().number(NumberLiteral.class),
                    Parser.rule().identifier(Name.class, reserved),
                    Parser.rule().string(StringLiteral.class));
    Parser factor = Parser.rule().or(Parser.rule(NegativeExpr.class).sep("-").ast(primary), primary);
    Parser expr = expr0.expression(BinaryExpr.class, factor, operators);

    Parser statement0 = Parser.rule();
    Parser block = Parser.rule(BlockStmt.class)
            .sep("{").option(statement0)
            .repeat(Parser.rule().sep(";", Token.EOL).option(statement0))
            .sep("}");
    Parser simple = Parser.rule(PrimaryExpr.class).ast(expr);
    Parser statement = statement0.or(
            Parser.rule(IfStmt.class).sep("if").ast(expr).ast(block)
                    .option(Parser.rule().sep("else").ast(block)),
            Parser.rule(WhileStmt.class).sep("while").ast(expr).ast(block),
            simple);

    Parser program = Parser.rule().or(statement, Parser.rule(NullStmt.class))
            .sep(";", Token.EOL);

    public BasicParser() {
        reserved.add(";");
        reserved.add("}");
        reserved.add(Token.EOL);

        operators.add("=", 1, Operators.RIGHT);
        operators.add("==", 2, Operators.LEFT);
        operators.add(">", 2, Operators.LEFT);
        operators.add(">=", 2, Operators.LEFT);
        operators.add("<", 2, Operators.LEFT);
        operators.add("<=", 2, Operators.LEFT);
        operators.add("+", 3, Operators.LEFT);
        operators.add("-", 3, Operators.LEFT);
        operators.add("*", 4, Operators.LEFT);
        operators.add("/", 4, Operators.LEFT);
        operators.add("%", 4, Operators.LEFT);
    }
    public ASTree parse(Lexer lexer) throws ParseException {
        return program.parse(lexer);
    }
}