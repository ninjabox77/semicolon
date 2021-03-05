package ast;

public abstract class Visitor<T extends Object> {
    
    public T visitHeader(Header h) {
        return h.visitChildren(this);
    }
    
    public T visitLine(Line li) {
        return li.visitChildren(this);
    }

    public T visitValue(Value val) {
        return null;
    }
    
    public T visitCompilation(Compilation c) {
        return c.visitChildren(this);
    }

    public T visitSequence(Sequence se) {
        return se.visitChildren(this);
    }
}
