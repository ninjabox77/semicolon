package scanner;

import java.util.ArrayList;
import java.util.List;

import ast.Token;
import ast.Type;

public class Lexer {
    
    private String src;
    private List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int pointer = 0;
    private int line = 1;
    
    private boolean consume = false;
    
    public Lexer(String src) {
        this.src = src;
    }
    
    private boolean isEOF() {
        return pointer >= src.length();
    }

    private boolean isDIGIT(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public boolean isLETTER(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }
    
    public boolean isALPHANUM(char ch) {
        return isLETTER(ch) || isDIGIT(ch) || ch == '_';
    }
    
    private void text() {
        char c = peek();
        while (!(c == '\\' || c == '\n') && !isEOF()) {
            next();
            c = peek();
        }
        String s = src.substring(start, pointer).trim();
        addToken(Type.MESSAGE, s);
    }
    
    private void ID() {
        while (isALPHANUM(peek()) && !isEOF()) {
            next();
        }
        String s = src.substring(start, pointer);
        addToken(Type.ID, s);
    }
    
    private void number() {
        while (isDIGIT(peek()) && !isEOF()) {
            next();
        }
        String s = src.substring(start, pointer);
        addToken(Type.INT, s);
    }
    
    private void WS() {
        char c = peek();
        while (c == ' ' || c == '\t' || c == '\r') {
            next();
            c = peek();
        }
    }

    private char peek() {
        if (isEOF())
            return '\0';
        return src.charAt(pointer);
    }
    
    private char prev() {
        return src.charAt(pointer - 1);
    }

    private char next() {
        char ch = src.charAt(pointer++);
        return ch;
    }

    private boolean match(char ch) {
        if (isEOF())
            return false;
        if (src.charAt(pointer) != ch)
            return false;
        ++pointer;
        return true;
    }
    
    private void scanToken() {
        char ch = next();
        switch (ch) {
        
        case '#':
            while (peek() != '\n' && !isEOF())
                next();
            break;
            
        case '!': addToken(Type.BANG); break;
        case '(': addToken(Type.LPAREN); break;
        case ')': addToken(Type.RPAREN); break;
        case '=': addToken(Type.EQUALS); consume = true; break;
        case '\\': addToken(Type.BACKSLASH); consume = true; break;
        case ':': addToken(Type.COLON); break;
        case '-': addToken(Type.MINUS); break;
        
        case ' ':
        case '\r':
        case '\t': WS(); break;

        case '\n': ++line; break;

        default:
            if (consume) {
                text();
                consume = false;
            }
            else if (isLETTER(ch))
                ID();
            else if (isDIGIT(ch))
                number();
            else
                throw new Error("Unexpected character; found'" + ch + "' @line " + line);
        }
    }

    private void addToken(int type, Object value) {
        String text = src.substring(start, pointer);
        tokens.add(new Token(text, type, line, start, pointer));
    }

    public void addToken(int type) {
        addToken(type, null);
    }

    public List<Token> scanTokens() {
        while (!isEOF()) {
            start = pointer;
            scanToken();
        }
        tokens.add(new Token("<EOF>", Type.EOF, line, start, pointer));
        return tokens;
    }
}
