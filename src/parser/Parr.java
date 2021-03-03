package parser;

import java.util.List;

import ast.*;

public class Parr {
    
    private final List<Token> tokens;
    private int current = 0;

    public Parr(List<Token> tokens) {
        this.tokens = tokens;
    }

    private boolean isEOF() {
        return peek().kind == Type.EOF;
    }

    private Token prev() {
        return tokens.get(current - 1);
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token next() {
        if (!isEOF())
            ++current;
        return prev();
    }
    
    private boolean check(int type) {
        if (isEOF())
            return false;
        return peek().kind == type;
    }

    private boolean match(Integer... types) {
        for (Integer type : types) {
            if (check(type)) {
                //next();
                return true;
            }
        }
        return false;
    }

    private Token consume(int type, String msg) {
        if (!check(type))
            throw new Error(msg);
        return next();
    }
    
    // ----------------------------------------------------------------------
    // GRAMMAR:
    //   file     : rows+
    //   rows     : '!' header
    //   header   : ID+ '(' DIGIT '-' DIGIT ') lines+
    //   lines    : ID '=' '\'? messages
    //   messages : TEXT ('\' messages)*
    //   ID       : ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|' ')+
    //   DIGIT    : ('0'..'9')+
    //   TEXT     : [a-zA-Z0-9 @:._/,%{}-]+
    // ----------------------------------------------------------------------
    
    public Compilation file() {
        Sequence<Header> se = rows();
        if (peek().kind != Type.EOF) {
            throw new Error("Too much input!");
        }
        return new Compilation(se);
    }
    
    public Sequence<Header> rows() {
        Sequence<Header> se = new Sequence<>();
        while (match(Type.BANG)) {
            next();
            se.append(header());
        }
        return se;
    }
    
    public Header header() {
        Sequence<Value> ids = new Sequence<>();
        if (!match(Type.ID)) {
            System.err.println("[header] !!Expecting ID, found '" + peek().lexeme + "' @line " + peek().line);
            System.exit(1);
        }
        ids.append(new Value(next()));
        while (match(Type.ID)) {
            ids.append(new Value(next()));
        }
        consume(Type.LPAREN, "[header] Expecting '(', found '" + peek().lexeme + "' @line " + peek().line);
        Literal left = new Literal(next());
        consume(Type.MINUS, "[header] Expecting '-', found '" + peek().lexeme + "' @line " + peek().line);
        Literal right = new Literal(next());
        consume(Type.RPAREN, "[header] Expecting ')', found '" + peek().lexeme + "' @line " + peek().line);
        RangeExpr re = new RangeExpr(left, right);
        Sequence<Line> se = new Sequence<>();
        while (match(Type.ID))
            se.append(lines());
        return new Header(ids, re, se);
    }
    
    public Line lines() {
        if (!match(Type.ID)) {
            System.err.println("[lines] Expecting ID, found '" + peek().lexeme + "' @line " + peek().line);
            System.exit(1);
        }
        Value id = new Value(next());
        consume(Type.EQUALS, "[lines] Expecting '=', found '" + peek().lexeme + "' @line " + peek().line);
        if (match(Type.BACKSLASH))
            next();
        Sequence<Value> se = new Sequence<>();
        if (!match(Type.MESSAGE)) {
            System.err.println("[lines] Expecting MESSAGE, found '" + peek().lexeme + "' @line " + peek().line);
            System.exit(1);
        }
        se.append(new Value(next()));
        while (match(Type.BACKSLASH)) {
            se.append(new Value(next()));
        }
        return new Line(id, se);
    }
}
