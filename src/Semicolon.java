import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import ast.AST;
import ast.Compilation;
import ast.Token;
import parser.Parr;
import printers.TreePrinter;
import scanner.Lexer;

public class Semicolon {
    
    public static void main(String[] args) {
        String src = "/Users/oswaldocisneros/eclipse-workspace/Semicolon/input/test";
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(Paths.get(src));
            src = new String(bytes, Charset.defaultCharset());
            Lexer lex = new Lexer(src);
            List<Token> tokens = lex.scanTokens();
//            for (Token t : tokens)
//                System.out.println(t);
            Parr parser = new Parr(tokens);
            Compilation c = parser.file();
            c.visit(new TreePrinter());
        } catch (IOException ex) {
        }
    }
}
