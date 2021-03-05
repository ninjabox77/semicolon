package ast;

public class Header extends AST {
    
    public Header(Value comment, Sequence<Line> lines) {
        nchildren = 2;
        children = new AST[] { comment, lines };
    }
    
    public Value comment() {
        return (Value) children[0];
    }
    
    public Sequence<Line> lines() {
        return (Sequence<Line>) children[1];
    }

    @Override
    public <W> W visit(Visitor<W> v) {
        return v.visitHeader(this);
    }
}
