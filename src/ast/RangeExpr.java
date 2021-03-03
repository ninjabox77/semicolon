package ast;

public class RangeExpr extends Expr {

    public RangeExpr(Expr left, Expr right) {
        super(left);
        nchildren = 2;
        children = new AST[] { left, right };
    }
    
    public Expr left() {
        return (Expr) children[0];
    }
    
    public Expr right() {
        return (Expr) children[1];
    }
    
    @Override
    public String toString() {
        return left() + "-" + right();
    }

    @Override
    public <W> W visit(Visitor<W> v) {
        return v.visitRangeExpr(this);
    }
}
