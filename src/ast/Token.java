package ast;

public class Token {
    
    public final String lexeme;
    public final int line;
    public final int start;
    public final int stop;
    public final int kind;
    
    public Token(String lexeme) {
        this(lexeme, 0, 0, 0, 0);
    }
    
    public Token(String lexeme, int kind, int line, int start, int stop) {
        this.lexeme = lexeme;
        this.kind = kind;
        this.line = line;
        this.start = start;
        this.stop = stop;
    }
    
    public String toString() {
        return "Token: '" + lexeme + "', line " + line +
                "[" + start + ":" + stop + "] (kind: " + kind + ")" ;
    }
}
