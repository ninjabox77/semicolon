package ast;

public class Header extends AST {
    
    public Header(Sequence<Value> keys, Expr range, Sequence<Line> lines) {
        nchildren = 3;
        children = new AST[] { keys, range, lines };
    }
    
    public Sequence<Value> keys() {
        return (Sequence<Value>) children[0];
    }
    
    public Expr range() {
        return (Expr) children[1];
    }
    
    public Sequence<Line> lines() {
        return (Sequence<Line>) children[2];
    }

    @Override
    public <W> W visit(Visitor<W> v) {
        return v.visitHeader(this);
    }
}
