import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import ast.*;
import parser.Parr;
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
        Writer writer = null;
        try {
            String outFile = new File("").getAbsolutePath() + File.separator + OUTFILE + ".txt";
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            for (String li : lines)
                writer.write(li + "\n");
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
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
//            c.visit(new TreePrinter());
            FileBuilder fb = new FileBuilder();
            c.visit(fb);
            List<String> lines = fb.lines();
            writeToFile(c, lines);
        } catch (IOException ex) {
        }
    }
}
