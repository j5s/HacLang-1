package org.hac.ast;

import java.util.List;

public class Fun extends ASTList {
    public Fun(List<ASTree> c) {
        super(c);
    }

    public ParameterList parameters() {
        return (ParameterList) child(0);
    }

    public BlockStmt body() {
        return (BlockStmt) child(1);
    }

    @Override
    public String toString() {
        return "(fun " + parameters() + " " + body() + ")";
    }
}
