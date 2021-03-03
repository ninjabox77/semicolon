package ast;

public class Literal extends Expr {
    
    private String text;

    public Literal(Token id) {
        super(id);
        text = id.lexeme;
    }
    
    @Override
    public String toString() {
        return text;
    }

    @Override
    public <W> W visit(Visitor<W> v) {
        return v.visitLiteral(this);
    }
}
