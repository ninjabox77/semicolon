package ast;

public abstract class Expr extends AST {
    
    public Expr(AST a) {
        super(a);
    }
    
    public Expr(Token token) {
        super(token);
    }
}
