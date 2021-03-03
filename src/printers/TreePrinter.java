package printers;

import ast.AST;
import ast.Compilation;
import ast.Header;
import ast.Line;
import ast.Literal;
import ast.RangeExpr;
import ast.Value;
import ast.Sequence;
import ast.Visitor;

public class TreePrinter extends Visitor<AST> {

    int indent = 0;

    private String indent(int line) {
        String s = "" + line + ": ";
        int l = 4 - s.length();
        for (int i = 0; i < indent + l; ++i)
            s = s + " ";
        return s;
    }
    
    public AST visitSequence(Sequence se) {
        System.out.println(indent(se.line) + "Sequence:[" + se.size() + " nodes]");
        int i = 0;
        for (Object o : se) {
            AST a = (AST) o;
            if (a != null) {
                System.out.println(indent(a.line) + "Sequence[" + i++ + "]:");
                indent += 2;
                a.visit(this);
                indent -= 2;
            } else
                System.out.println(indent(a.line) + "Sequence[" + i++ + "]: = null");
        }
        return null;
    }
    
    public AST visitCompilation(Compilation c) {
        System.out.println(indent(c.line) + "Compilation: ");
        indent += 2;
        super.visitCompilation(c);
        indent -= 2;
        return null;
    }

    public AST visitValue(Value val) {
        System.out.println(indent(val.line) + "Value: " + val.text);
        return null;
    }
    
    public AST visitRangeExpr(RangeExpr re) {
        System.out.println(indent(re.line) + "Range: " + re);
        indent += 2;
        super.visitRangeExpr(re);
        indent -= 2;
        return null;
    }
    
    public AST visitHeader(Header h) {
        System.out.println(indent(h.line) + "Header: ");
        indent += 2;
        super.visitHeader(h);
        indent -= 2;
        return null;
    }
    
    public AST visitLine(Line li) {
        System.out.println(indent(li.line) + "Line: ");
        indent += 2;
        super.visitLine(li);
        indent -= 2;
        return null;
    }
    
    public AST visitLiteral(Literal li) {
        System.out.println(indent(li.line) + "Literal: " + li);
        indent += 2;
        super.visitLiteral(li);
        indent -= 2;
        return null;
    }
}
