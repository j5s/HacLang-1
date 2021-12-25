package org.hac.core;

public class StrToken extends Token {
    private final String literal;

    public StrToken(int line, String str) {
        super(line);
        literal = str;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String getText() {
        return literal;
    }
}
