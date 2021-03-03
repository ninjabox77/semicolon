package ast;

public class Value extends AST {
    
    public String text;
    
    public Value(Token id) {
        super(id);
        text = id.lexeme;
    }
    
    @Override
    public String toString() {
        return token.lexeme;
    }

    @Override
    public <W> W visit(Visitor<W> v) {
        return v.visitValue(this);
    }
}
