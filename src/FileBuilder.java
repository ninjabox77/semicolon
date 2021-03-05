import java.util.ArrayList;
import java.util.List;

import ast.*;
import utilities.Log;

public class FileBuilder extends Visitor<String> {
    
    List<String> list = new ArrayList<>();
    
    private static final String COMMENT = "COMMENT";
    private static final String KEY = "KEY";
    private static final String VALUE = "VALUE";
    
    public FileBuilder() {
        Log.logHeader("========================================");
        Log.logHeader("*             W R I T E R              *");
        Log.logHeader("========================================");
    }
    
    public String visitCompilation(Compilation c) {
        Log.log(c, "Visiting a Compilation");
        c.headers().visit(this);
        return null;
    }
    
    public String visitHeader(Header h) {
        Log.log(h, "Visiting a Header");
        list.add(COMMENT + " = " + h.comment().visit(this));
        for (Line li : h.lines())
            li.visit(this);
        return null;
    }
    
    public String visitLine(Line li) {
        Log.log(li, "Visiting a Line");
        list.add(KEY + " = " + li.key().visit(this));
        for (Value val : li.lines())
            list.add(VALUE + " = " + val.visit(this));
        return null;
    }

    public String visitValue(Value val) {
        Log.log(val, "Visiting a Value");
        return val.text;
    }

    public String visitSequence(Sequence se) {
        Log.log(se, "Visiting a Sequence");
        for (int i = 0; i < se.size(); ++i) {
            if (se.child(i) != null)
                se.child(i).visit(this);
        }
        return null;
    }
    
    public List<String> lines() {
        return list;
    }
}
