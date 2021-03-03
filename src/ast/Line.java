package ast;

public class Line extends AST {
    
    public Line(Value key, Sequence<Value> lines) {
        nchildren = 2;
        children = new AST[] { key, lines };
    }
    
    public Value key() {
        return (Value) children[0];
    }
    
    public Sequence<Value> lines() {
        return (Sequence<Value>) children[1];
    }

    @Override
    public <W> W visit(Visitor<W> v) {
        return v.visitLine(this);
    }
}
