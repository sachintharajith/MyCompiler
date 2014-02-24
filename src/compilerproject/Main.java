/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilerproject;
import java.io.File;
import lexer.*;
import parser.*;
/**
 *
 * @author vindyani
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
         File inFile = null;
         if (0 < args.length) {
                inFile = new File(args[0]);
            }
         else{
                throw new Exception("input source file not specified");
         }
        Lexer l = new Lexer(inFile);
        Parser p = new Parser(l);
        p.program();
    }

}
