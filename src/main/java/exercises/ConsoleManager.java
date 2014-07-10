package exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;
public class ConsoleManager {
    private static Console console=System.console();
    
    public static void printf(String formatString, Object ... objs){
        try {
            if (console != null) {
                console.printf(formatString, objs);
            } else {
                System.out.print(String.format(formatString, objs));
            }
        } catch (IllegalFormatException e) {
            System.err.print("Error printing...\n");
        }
    }
    
    public static void print(String content){
        printf("%s",content);
    }
    
    public static String promptForUserInput(String prompt) throws IOException{
        String line = "";
        
        if (console != null) {
            line = console.readLine(prompt);
        } else {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            print(prompt);
            line = bufferedReader.readLine();
        }
        line.trim();
        return line;
    }

}
