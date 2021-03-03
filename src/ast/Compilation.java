package ast;

public class Compilation extends AST {
    
    public Compilation(Sequence<Header> headers) {
        nchildren = 1;
        children = new AST[] { headers };
    }
    
    public Sequence<Header> headers() {
        return (Sequence<Header>) children[0];
    }

    @Override
    public <W> W visit(Visitor<W> v) {
        return v.visitCompilation(this);
    }
}
