import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import ast.*;
import parser.Parser;
import printers.TreePrinter;
import scanner.Lexer;
import utilities.Log;

public class Semicolon {
    
    // Local path of properties file in parent directory
    private final static String PATH = "processj/resources/properties/VisitorMessageNumber.properties";
    
    private final static String OUTFILE = "ERROR_MSG";
    
    // TODO:
    public static void writeToFile(Compilation c, List<String> lines) {
        Log.log(c, "Writing to file... ");
        try {
            String outFile = new File("").getAbsolutePath() + 
                    File.separator + OUTFILE + ".txt";
            FileWriter file = new FileWriter(outFile);
            BufferedWriter bw = new BufferedWriter(file);
            // write lines to output file
            for (String li : lines) {
                bw.write(li);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static void main(String[] args) {
        String src = "/Users/oswaldocisneros/eclipse-workspace/Semicolon/input/test";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(src));
            src = new String(bytes, Charset.defaultCharset());
            Lexer lex = new Lexer(src);
            List<Token> tokens = lex.scanTokens();
//            for (Token t : tokens)
//                System.out.println(t);
            Parser parser = new Parser(tokens);
            Compilation c = parser.file();
//            c.visit(new TreePrinter());
            FileVistor fv = new FileVistor();
            c.visit(fv);
            List<String> lines = fv.lines();
            writeToFile(c, lines);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
