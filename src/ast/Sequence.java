package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sequence<T extends AST> extends AST implements Iterable<T> {
    
    public List<T> children = new ArrayList<>();
    
    public Sequence() { }
    
    public Sequence(T child) {
        children.add(child);
    }
    
    public T child(int i) {
        return children.get(i);
    }
    
    public Sequence<T> append(T element) {
        children.add(element);
        return this;
    }
    
    public <S extends T> Sequence<T> merge(Sequence<S> other) {
        for (T e : other)
            children.add(e);
        return this;
    }
    
    public void clear() {
        children.clear();
    }
    
    public int size() {
        return children.size();
    }
    
    public void set(int i, T child) {
        children.set(i, child);
    }

    @Override
    public Iterator<T> iterator() {
        return children.iterator();
    }

    @Override
    public <W> W visit(Visitor<W> v) {
        return v.visitSequence(this);
    }
}
