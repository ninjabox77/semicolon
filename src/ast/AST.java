package ast;

public abstract class AST {
    
    public AST[] children;
    public int nchildren = 0;
    public Token token;
    public int line;
    public int charBegin;
    
    public AST() {
    }

    public AST(AST a) {
        if (a == null) {
            line = 0;
            charBegin = 0;
        } else {
            line = a.line;
            charBegin = a.charBegin;
        }
    }

    public AST(Token token) {
        this.token = token;
        this.line = token.line;
    }

    public abstract <W extends Object> W visit(Visitor<W> v);

    public <T extends Object> T visitChildren(Visitor<T> v) {
        for (int i = 0; i < nchildren; ++i)
            if (children[i] != null)
                children[i].visit(v);
        return null;
    }
}
