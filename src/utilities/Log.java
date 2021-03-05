package utilities;

import ast.AST;

public class Log {
    
    public static boolean doLog = false;

    public static void startLogging() {
        doLog = true;
    }

    public static void stopLogging() {
        doLog = false;
    }
    
    private Log() { }
    
    public static void log(AST a, String s) {
        if (doLog) {
            System.out.println(a.line + ": " + s);
        }
    }
    
    public static void logHeader(String s) {
        if (doLog) {
            System.out.println(s);
        }
    }
}
